package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import semtd_intranet.semtd_net.DTO.ProjetoDTO;
import semtd_intranet.semtd_net.service.ProjetoService;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<ProjetoDTO> criar(@Valid @RequestBody ProjetoDTO dto) {
        return ResponseEntity.ok(projetoService.salvar(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<ProjetoDTO>> listar() {
        return ResponseEntity.ok(projetoService.listarTodos());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/por-gerencia")
    public ResponseEntity<List<ProjetoDTO>> listarPorGerencia(@RequestParam Long idGerencia) {
        return ResponseEntity.ok(projetoService.listarPorGerencia(idGerencia));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> buscar(@PathVariable Long id) {
        return projetoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/por-nome")
    public ResponseEntity<ProjetoDTO> buscarPorNome(@RequestParam String nome) {
        return projetoService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/por-nome")
    public ResponseEntity<ProjetoDTO> atualizarPorNome(@RequestParam String nome, @Valid @RequestBody ProjetoDTO dto) {
        try {
            return ResponseEntity.ok(projetoService.atualizarPorNome(nome, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/por-nome")
    public ResponseEntity<String> deletarPorNome(@RequestParam String nome) {
        if (!projetoService.existePorNome(nome)) {
            return ResponseEntity.notFound().build();
        }
        projetoService.deletarPorNome(nome);
        return ResponseEntity.ok("Projeto deletado com sucesso.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProjetoDTO dto) {
        try {
            return ResponseEntity.ok(projetoService.atualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (!projetoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        projetoService.deletar(id);
        return ResponseEntity.ok("Projeto deletado com sucesso.");
    }
}
