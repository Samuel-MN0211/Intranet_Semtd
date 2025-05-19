
//UTILIZADO PARA AUTENTICAÇÃO HTTP SIMPLES. DESCARTAR AO IMPLEMENTAR JWT 

package semtd_intranet.semtd_net.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.UsuariosRepository;
import semtd_intranet.semtd_net.security.SecurityConfig;
import semtd_intranet.semtd_net.service.UsuariosService;
import semtd_intranet.semtd_net.enums.Cargo;
import semtd_intranet.semtd_net.enums.Role;
import semtd_intranet.semtd_net.service.ArquivosService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuariosService usuarioService;

    @Autowired
    private ArquivosService arquivosService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastraradmin")
    public ResponseEntity<?> cadastrarAdmin(@RequestBody Usuarios admin) {
        if (usuariosRepository.existsByEmail(admin.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        admin.setRoles(Set.of(Role.ADMIN));
        usuariosRepository.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body("Admin cadastrado com sucesso");
    }

    // MODIFICAR CADASTRO DE NOVOS ADMS AO IR PARA PRODUÇÃO / MUDAR REGRA DE NEGOCIO
    // NO FRONT. ATUALMENTE ESTA CONFIGURADA PARA NÃO EXIGIR MUITOS PARAMETROS
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuarioComFoto(
            @RequestParam("nome") String nome,
            @RequestParam("senha") String senha,
            @RequestParam("email") String email,
            @RequestParam(value = "cargo", required = false) Cargo cargo,
            @RequestParam(value = "formacao", required = false) String formacao,
            @RequestParam(value = "gerenciaId", required = false) Long gerenciaId,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {

        if (usuariosRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        Usuarios usuario = new Usuarios();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setCargo(cargo);
        usuario.setFormacao(formacao);
        usuario.setRoles(Set.of(Role.USUARIO));

        if (gerenciaId != null) {
            Gerencia gerencia = new Gerencia();
            gerencia.setId(gerenciaId);
            usuario.setGerencia(gerencia);
        }

        if (foto != null && !foto.isEmpty()) {
            try {
                Arquivos fotoSalva = arquivosService.salvarArquivo(foto);
                usuario.setFotoUsuario(fotoSalva);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar foto.");
            }
        }

        usuariosRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
    }

}
