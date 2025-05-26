package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.SistemasDTO;
import semtd_intranet.semtd_net.model.Sistemas;
import semtd_intranet.semtd_net.service.SistemasService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sistemas")

public class SistemasController {

    @Autowired
    private SistemasService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarSistema(@Valid @RequestBody SistemasDTO dto) {
        Sistemas sistema = new Sistemas();
        sistema.setNome(dto.getNome());
        sistema.setDescricao(dto.getDescricao());
        sistema.setLink(dto.getLink());
        return ResponseEntity.ok(service.salvar(sistema));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<Sistemas>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // CRUD com ID de parâmetro - PATHVARIABLE
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Sistemas> sistema = service.buscarPorId(id);
        return sistema.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @Valid @RequestBody SistemasDTO dto) {
        return service.buscarPorId(id).map(s -> {
            s.setNome(dto.getNome());
            s.setDescricao(dto.getDescricao());
            s.setLink(dto.getLink());
            return ResponseEntity.ok(service.salvar(s));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {
        if (!service.existePorId(id))
            return ResponseEntity.notFound().build();
        service.deletarPorId(id);
        return ResponseEntity.ok("Sistema deletado com sucesso.");
    }

    // CRUD com título de parâmetro - REQUESTPARAM
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        Optional<Sistemas> sistema = service.buscarPorNome(nome);
        return sistema.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarPorNome(@RequestParam String nome, @Valid @RequestBody SistemasDTO dto) {
        return service.buscarPorNome(nome).map(s -> {
            s.setNome(dto.getNome());
            s.setDescricao(dto.getDescricao());
            s.setLink(dto.getLink());
            return ResponseEntity.ok(service.salvar(s));
        }).orElseGet(() -> ResponseEntity.notFound().build());
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
