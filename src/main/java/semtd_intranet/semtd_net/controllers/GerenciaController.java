package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import semtd_intranet.semtd_net.DTO.GerenciaDTO;
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
        return ResponseEntity.ok(gerenciaService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<GerenciaDTO> getGerencia(@PathVariable Long id) {
        return ResponseEntity.ok(gerenciaService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/vinculadas/{idGerenciaExecutiva}")
    public ResponseEntity<List<GerenciaDTO>> getGerenciasVinculadas(@PathVariable Long idGerenciaExecutiva) {
        return ResponseEntity.ok(gerenciaService.findGerenciasVinculadas(idGerenciaExecutiva));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/criar")
    public ResponseEntity<GerenciaDTO> createGerencia(@RequestBody @Valid GerenciaDTO dto) {
        GerenciaDTO criada = gerenciaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar/{id}")
    public ResponseEntity<GerenciaDTO> updateGerencia(@PathVariable Long id,
            @RequestBody @Valid GerenciaDTO dto) {
        return ResponseEntity.ok(gerenciaService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteGerencia(@PathVariable Long id) {
        gerenciaService.delete(id);
        return ResponseEntity.ok("Gerência deletada com sucesso.");
    }
}
