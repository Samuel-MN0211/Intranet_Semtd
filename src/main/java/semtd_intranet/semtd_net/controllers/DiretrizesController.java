package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import semtd_intranet.semtd_net.model.Diretrizes;
import semtd_intranet.semtd_net.service.DiretrizesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/diretrizes")
@PreAuthorize("hasRole('ADMIN')")
public class DiretrizesController {

    @Autowired
    private DiretrizesService diretrizesService;

    @PostMapping
    public ResponseEntity<?> criarDiretriz(@Valid @RequestBody Diretrizes diretriz) {
        return ResponseEntity.ok(diretrizesService.salvar(diretriz));
    }

    @GetMapping
    public ResponseEntity<List<Diretrizes>> listarTodas() {
        return ResponseEntity.ok(diretrizesService.listarTodas());
    }

    // CRUD PARÂMETRO ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Diretrizes> diretriz = diretrizesService.buscarPorId(id);
        return diretriz.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDiretriz(@PathVariable Long id, @Valid @RequestBody Diretrizes novaDiretriz) {
        return diretrizesService.buscarPorId(id).map(d -> {
            d.setTitulo(novaDiretriz.getTitulo());
            d.setDescricao(novaDiretriz.getDescricao());
            diretrizesService.salvar(d);
            return ResponseEntity.ok(d);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarDiretriz(@PathVariable Long id) {
        if (!diretrizesService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        diretrizesService.deletarPorId(id);
        return ResponseEntity.ok("Diretriz deletada com sucesso.");
    }

    // CRUD PARÂMETRO TITULO

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<?> buscarPorTitulo(@PathVariable String titulo) {
        Optional<Diretrizes> diretriz = diretrizesService.buscarPorTitulo(titulo);
        return diretriz.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/titulo/{titulo}")
    public ResponseEntity<?> atualizarPorTitulo(@PathVariable String titulo,
            @Valid @RequestBody Diretrizes novaDiretriz) {
        return diretrizesService.buscarPorTitulo(titulo).map(d -> {
            d.setTitulo(novaDiretriz.getTitulo());
            d.setDescricao(novaDiretriz.getDescricao());
            diretrizesService.salvar(d);
            return ResponseEntity.ok(d);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/titulo/{titulo}")
    public ResponseEntity<?> deletarPorTitulo(@PathVariable String titulo) {
        if (!diretrizesService.existePorTitulo(titulo)) {
            return ResponseEntity.notFound().build();
        }
        diretrizesService.deletarPorTitulo(titulo);
        return ResponseEntity.ok("Diretriz deletada com sucesso.");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<String>> listarMetadados() {
        List<String> metadados = diretrizesService.listarTodas().stream()
                .map(d -> "ID: " + d.getId() + ", Título: " + d.getTitulo() + ", Criado em: " + d.getCriadoEm())
                .toList();
        return ResponseEntity.ok(metadados);
    }
}
