package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.CardsBaseDTO;

import semtd_intranet.semtd_net.service.CardsBaseService;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsBaseController {

    @Autowired
    private CardsBaseService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<CardsBaseDTO> criarCard(@Valid @RequestBody CardsBaseDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<CardsBaseDTO>> listarTodos() {
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
    public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @Valid @RequestBody CardsBaseDTO dto) {
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
        return ResponseEntity.ok("Card deletado com sucesso.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar")
    public ResponseEntity<List<CardsBaseDTO>> buscarPorFragmentoNome(@RequestParam String nome) {
        List<CardsBaseDTO> resultados = service.buscarPorFragmentoNome(nome);
        return ResponseEntity.ok(resultados);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarPorNome(@RequestParam String nome, @Valid @RequestBody CardsBaseDTO dto) {
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
        return ResponseEntity.ok("Card deletado com sucesso.");
    }
}
