package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.DiretrizesDTO;

import semtd_intranet.semtd_net.service.DiretrizesService;

import java.util.List;

@RestController
@RequestMapping("/diretrizes")
public class DiretrizesController {

    @Autowired
    private DiretrizesService diretrizesService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<DiretrizesDTO> criarDiretriz(@Valid @RequestBody DiretrizesDTO dto) {
        DiretrizesDTO salvo = diretrizesService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<DiretrizesDTO>> listarTodas() {
        return ResponseEntity.ok(diretrizesService.listarTodas());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<DiretrizesDTO> buscarPorId(@PathVariable Long id) {
        return diretrizesService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DiretrizesDTO> atualizarDiretriz(@PathVariable Long id,
            @Valid @RequestBody DiretrizesDTO dto) {
        try {
            DiretrizesDTO atualizado = diretrizesService.atualizarPorId(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<DiretrizesDTO> buscarPorTitulo(@RequestParam String titulo) {
        return diretrizesService.buscarPorTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<DiretrizesDTO> atualizarPorTitulo(@RequestParam String titulo,
            @Valid @RequestBody DiretrizesDTO dto) {
        try {
            DiretrizesDTO atualizado = diretrizesService.atualizarPorTitulo(titulo, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
}
