
//UTILIZADO PARA AUTENTICAÇÃO HTTP SIMPLES. DESCARTAR AO IMPLEMENTAR JWT 

package semtd_intranet.semtd_net.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import semtd_intranet.semtd_net.DTO.UsuarioCadastroDTO;
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
    @PostMapping("/cadastrarusuario")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuarios usuario) {
        if (usuariosRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRoles(Set.of(Role.USUARIO)); // sempre será USUARIO

        usuariosRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
    }
}