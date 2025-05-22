package semtd_intranet.semtd_net.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import semtd_intranet.semtd_net.DTO.UsuarioCadastroDTO;
import semtd_intranet.semtd_net.enums.Role;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.GerenciaRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private GerenciaRepository gerenciaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastraradmin")
    public ResponseEntity<?> cadastrarAdmin(@Valid @RequestBody UsuarioCadastroDTO dto) {
        if (usuariosRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        Gerencia gerencia = gerenciaRepository.findById(dto.getGerenciaId()).orElse(null);
        if (gerencia == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Gerência não encontrada");
        }

        Usuarios admin = new Usuarios();
        admin.setNome(dto.getNome());
        admin.setSenha(passwordEncoder.encode(dto.getSenha()));
        admin.setEmail(dto.getEmail());
        admin.setCargo(dto.getCargo());
        admin.setFormacao(dto.getFormacao());
        admin.setGerencia(gerencia);
        admin.setRoles(Set.of(Role.ADMIN));

        usuariosRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin cadastrado com sucesso");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrarusuario")
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto) {
        if (usuariosRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        Gerencia gerencia = gerenciaRepository.findById(dto.getGerenciaId()).orElse(null);
        if (gerencia == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Gerência não encontrada");
        }

        Usuarios usuario = new Usuarios();
        usuario.setNome(dto.getNome());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setEmail(dto.getEmail());
        usuario.setCargo(dto.getCargo());
        usuario.setFormacao(dto.getFormacao());
        usuario.setGerencia(gerencia);
        usuario.setRoles(Set.of(Role.USUARIO));

        usuariosRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<?> listarUsuarios() {
        var usuarios = usuariosRepository.findAll().stream().map(u -> {
            String tipo = u.getRoles().contains(Role.ADMIN) ? "ADMIN" : "USUARIO";
            return String.format("Nome: %s | Email: %s | Tipo: %s", u.getNome(), u.getEmail(), tipo);
        }).toList();
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deletarUsuarioPorEmail(@RequestParam String email) {
        Usuarios usuario = usuariosRepository.findByEmail(email).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        usuariosRepository.delete(usuario);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

}
