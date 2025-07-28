package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import semtd_intranet.semtd_net.DTO.FeriasCadastroDTO;
import semtd_intranet.semtd_net.DTO.FeriasDTO;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.service.FeriasService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ferias")
public class FeriasController {

    @Autowired
    private FeriasService feriasService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FeriasDTO> criar(
            @Valid @RequestBody FeriasCadastroDTO dto,
            @AuthenticationPrincipal Usuarios supervisor) {

        return ResponseEntity.ok(feriasService.cadastrar(dto, supervisor.getRealUsername()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping
    public ResponseEntity<List<FeriasDTO>> listar() {
        return ResponseEntity.ok(feriasService.listarTodos());
    }

    @GetMapping("/por-mes")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<List<FeriasDTO>> listarPorMesEAno(
            @RequestParam("mes") int mes,
            @RequestParam("ano") int ano) {
        return ResponseEntity.ok(feriasService.listarPorMesEAno(mes, ano));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<FeriasDTO> feriasDTO = feriasService.buscarPorIdDTO(id);
        if (feriasDTO.isPresent()) {
            return ResponseEntity.ok(feriasDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Férias com ID " + id + " não encontrada");
        }
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    public ResponseEntity<List<FeriasDTO>> buscarPorRealUsername(@RequestParam("realUsername") String realUsername) {
        List<FeriasDTO> resultados = feriasService.buscarPorRealUsername(realUsername);
        return ResponseEntity.ok(resultados);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FeriasCadastroDTO dto,
            @AuthenticationPrincipal Usuarios supervisor) {

        try {
            return ResponseEntity.ok(feriasService.atualizar(id, dto, supervisor.getRealUsername()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            feriasService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Erro ao deletar: " + e.getMessage());
        }
    }
}
