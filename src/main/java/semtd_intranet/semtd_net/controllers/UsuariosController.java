package semtd_intranet.semtd_net.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import semtd_intranet.semtd_net.DTO.RedefinirSenhaAdminDTO;
import semtd_intranet.semtd_net.DTO.UsuarioCadastroDTO;
import semtd_intranet.semtd_net.DTO.UsuarioRespostaDTO;
import semtd_intranet.semtd_net.enums.Role;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.service.UsuariosService;

@RestController
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/cadastraradmin")
    public ResponseEntity<?> cadastrarAdmin(@Valid @RequestBody UsuarioCadastroDTO dto) {
        if (usuariosService.emailExiste(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        try {
            usuariosService.cadastrarUsuario(dto, Role.ADMIN);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrarusuario")
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto) {
        if (usuariosService.emailExiste(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }

        try {
            usuariosService.cadastrarUsuario(dto, Role.USUARIO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioRespostaDTO>> listarUsuarios() {
        List<UsuarioRespostaDTO> usuarios = usuariosService.listarTodos().stream()
                .map(u -> new UsuarioRespostaDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getRoles().contains(Role.ADMIN) ? "ADMIN" : "USUARIO",
                        u.getRealUsername()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    // Listar Usuários por ID de gerência
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/por-gerencia/{idGerencia}")
    public ResponseEntity<List<String>> listarUsuariosPorGerencia(@PathVariable Long idGerencia) {
        List<Usuarios> usuarios = usuariosService.listarPorGerencia(idGerencia);

        List<String> resposta = usuarios.stream()
                .map(u -> String.format("Nome: %s | Email: %s | Função: %s", u.getNome(), u.getEmail(),
                        u.getFormacao()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

    // Listar usuarios por nome de gerência

    @GetMapping("/por-nome-gerencia")
    public ResponseEntity<List<String>> listarUsuariosPorNomeGerencia(@RequestParam String nome) {
        List<Usuarios> usuarios = usuariosService.listarPorNomeGerencia(nome);

        List<String> resposta = usuarios.stream()
                .map(u -> String.format("Nome: %s | Email: %s | Função: %s", u.getNome(), u.getEmail(),
                        u.getFormacao()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletarUsuarioPorEmail(@RequestParam String email) {
        var usuario = usuariosService.buscarPorEmail(email);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        usuariosService.deletarPorEmail(email);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    @PutMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody @Valid RedefinirSenhaAdminDTO dto) {
        try {
            usuariosService.redefinirSenhaPorId(dto.getUsuarioId(), dto.getNovaSenha());
            return ResponseEntity.ok("Senha redefinida com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
