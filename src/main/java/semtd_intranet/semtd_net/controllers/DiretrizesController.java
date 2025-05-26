package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.DiretrizesDTO;
import semtd_intranet.semtd_net.model.Diretrizes;
import semtd_intranet.semtd_net.service.DiretrizesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/diretrizes")
public class DiretrizesController {

    @Autowired
    private DiretrizesService diretrizesService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> criarDiretriz(@Valid @RequestBody DiretrizesDTO dto) {
        Diretrizes diretriz = new Diretrizes();
        diretriz.setTitulo(dto.getTitulo());
        diretriz.setDescricao(dto.getDescricao());
        return ResponseEntity.ok(diretrizesService.salvar(diretriz));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("listar")
    public ResponseEntity<List<Diretrizes>> listarTodas() {
        return ResponseEntity.ok(diretrizesService.listarTodas());
    }

    // CRUD com ID de parâmetro - PATHVARIABLE
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Diretrizes> diretriz = diretrizesService.buscarPorId(id);
        return diretriz.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDiretriz(@PathVariable Long id, @Valid @RequestBody DiretrizesDTO dto) {
        return diretrizesService.buscarPorId(id).map(d -> {
            d.setTitulo(dto.getTitulo());
            d.setDescricao(dto.getDescricao());
            diretrizesService.salvar(d);
            return ResponseEntity.ok(d);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarDiretriz(@PathVariable Long id) {
        if (!diretrizesService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        diretrizesService.deletarPorId(id);
        return ResponseEntity.ok("Diretriz deletada com sucesso.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorTitulo(@RequestParam String titulo) {
        Optional<Diretrizes> diretriz = diretrizesService.buscarPorTitulo(titulo);
        return diretriz.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarPorTitulo(@RequestParam String titulo, @Valid @RequestBody DiretrizesDTO dto) {
        return diretrizesService.buscarPorTitulo(titulo).map(d -> {
            d.setTitulo(dto.getTitulo());
            d.setDescricao(dto.getDescricao());
            diretrizesService.salvar(d);
            return ResponseEntity.ok(d);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarPorTitulo(@RequestParam String titulo) {
        if (!diretrizesService.existePorTitulo(titulo)) {
            return ResponseEntity.notFound().build();
        }
        diretrizesService.deletarPorTitulo(titulo);
        return ResponseEntity.ok("Diretriz deletada com sucesso.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listarmetadados")
    public ResponseEntity<List<String>> listarMetadados() {
        List<String> metadados = diretrizesService.listarTodas().stream()
                .map(d -> "ID: " + d.getId() + ", Título: " + d.getTitulo() + ", Criado em: " + d.getCriadoEm())
                .toList();
        return ResponseEntity.ok(metadados);
    }
}
