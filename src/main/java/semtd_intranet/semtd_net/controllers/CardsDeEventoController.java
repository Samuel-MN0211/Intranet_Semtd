package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.CardsDeEventoDTO;
import semtd_intranet.semtd_net.model.CardsDeEvento;
import semtd_intranet.semtd_net.service.CardsDeEventoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards-evento")
public class CardsDeEventoController {

    @Autowired
    private CardsDeEventoService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> criar(@Valid @RequestBody CardsDeEventoDTO dto) {
        try {
            CardsDeEvento card = new CardsDeEvento(null, dto.getTitulo(), dto.getData(), dto.getHorarioInicio(),
                    dto.getHorarioFim(), dto.getLocalizacao(), dto.getLink());
            return ResponseEntity.ok(service.salvar(card));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<CardsDeEvento>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // CRUD com ID de parâmetro - PATHVARIABLE
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<CardsDeEvento> card = service.buscarPorId(id);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @Valid @RequestBody CardsDeEventoDTO dto) {
        return service.buscarPorId(id).map(card -> {
            card.setTitulo(dto.getTitulo());
            card.setData(dto.getData());
            card.setHorarioInicio(dto.getHorarioInicio());
            card.setHorarioFim(dto.getHorarioFim());
            card.setLocalizacao(dto.getLocalizacao());
            card.setLink(dto.getLink());
            return ResponseEntity.ok(service.salvar(card));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {
        if (!service.existePorId(id))
            return ResponseEntity.notFound().build();
        service.deletarPorId(id);
        return ResponseEntity.ok("Card deletado com sucesso.");
    }

    // CRUD com titulo de parâmetro - requestparam
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorTitulo(@RequestParam String titulo) {
        Optional<CardsDeEvento> card = service.buscarPorTitulo(titulo);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarPorTitulo(@RequestParam String titulo, @Valid @RequestBody CardsDeEventoDTO dto) {
        return service.buscarPorTitulo(titulo).map(card -> {
            card.setTitulo(dto.getTitulo());
            card.setData(dto.getData());
            card.setHorarioInicio(dto.getHorarioInicio());
            card.setHorarioFim(dto.getHorarioFim());
            card.setLocalizacao(dto.getLocalizacao());
            card.setLink(dto.getLink());
            return ResponseEntity.ok(service.salvar(card));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarPorTitulo(@RequestParam String titulo) {
        if (!service.existePorTitulo(titulo))
            return ResponseEntity.notFound().build();
        service.deletarPorTitulo(titulo);
        return ResponseEntity.ok("Card deletado com sucesso.");
    }
}
