package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.CardsDeEventoDTO;

import semtd_intranet.semtd_net.service.CardsDeEventoService;

import java.util.List;

@RestController
@RequestMapping("/cards-evento")
public class CardsDeEventoController {

    @Autowired
    private CardsDeEventoService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> criar(@Valid @RequestBody CardsDeEventoDTO dto) {
        try {
            return ResponseEntity.ok(service.salvar(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<CardsDeEventoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<CardsDeEventoDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CardsDeEventoDTO> atualizarPorId(@PathVariable Long id,
            @Valid @RequestBody CardsDeEventoDTO dto) {
        try {
            return ResponseEntity.ok(service.atualizarPorId(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPorId(@PathVariable Long id) {
        if (!service.existePorId(id))
            return ResponseEntity.notFound().build();
        service.deletarPorId(id);
        return ResponseEntity.ok("Card deletado com sucesso.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar")
    public ResponseEntity<CardsDeEventoDTO> buscarPorTitulo(@RequestParam String titulo) {
        return service.buscarPorTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<CardsDeEventoDTO> atualizarPorTitulo(@RequestParam String titulo,
            @Valid @RequestBody CardsDeEventoDTO dto) {
        try {
            return ResponseEntity.ok(service.atualizarPorTitulo(titulo, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar")
    public ResponseEntity<String> deletarPorTitulo(@RequestParam String titulo) {
        if (!service.existePorTitulo(titulo))
            return ResponseEntity.notFound().build();
        service.deletarPorTitulo(titulo);
        return ResponseEntity.ok("Card deletado com sucesso.");
    }
}
