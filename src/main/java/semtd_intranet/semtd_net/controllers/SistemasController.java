package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.SistemasDTO;

import semtd_intranet.semtd_net.service.SistemasService;

import java.util.List;

@RestController
@RequestMapping("/sistemas")
public class SistemasController {

    @Autowired
    private SistemasService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<SistemasDTO> criarSistema(@Valid @RequestBody SistemasDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<SistemasDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @Valid @RequestBody SistemasDTO dto) {
        try {
            return ResponseEntity.ok(service.atualizarPorId(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {
        if (!service.existePorId(id))
            return ResponseEntity.notFound().build();
        service.deletarPorId(id);
        return ResponseEntity.ok("Sistema deletado com sucesso.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarPorNome(@RequestParam String nome, @Valid @RequestBody SistemasDTO dto) {
        try {
            return ResponseEntity.ok(service.atualizarPorNome(nome, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarPorNome(@RequestParam String nome) {
        if (!service.existePorNome(nome))
            return ResponseEntity.notFound().build();
        service.deletarPorNome(nome);
        return ResponseEntity.ok("Sistema deletado com sucesso.");
    }
}
