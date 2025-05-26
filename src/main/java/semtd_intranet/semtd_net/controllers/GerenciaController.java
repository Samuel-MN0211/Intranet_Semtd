package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import semtd_intranet.semtd_net.DTO.GerenciaDTO;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.service.GerenciaService;
import java.util.List;

@RestController
@RequestMapping("/gerencia")
public class GerenciaController {

    @Autowired
    private GerenciaService gerenciaService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<GerenciaDTO>> getAllGerencias() {
        List<Gerencia> gerencias = gerenciaService.findAll();
        List<GerenciaDTO> dtos = gerencias.stream()
                .map(GerenciaDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<GerenciaDTO> getGerencia(@PathVariable Long id) {
        Gerencia gerencia = gerenciaService.findById(id);
        return ResponseEntity.ok(new GerenciaDTO(gerencia));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/criar")
    public ResponseEntity<Gerencia> createGerencia(@RequestBody @Valid GerenciaDTO dto) {
        Gerencia criada = gerenciaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar/{id}")
    public ResponseEntity<Gerencia> updateGerencia(
            @PathVariable Long id,
            @RequestBody @Valid GerenciaDTO dto) {
        Gerencia atualizada = gerenciaService.update(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteGerencia(@PathVariable Long id) {
        gerenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
