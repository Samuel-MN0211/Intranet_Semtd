package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import semtd_intranet.semtd_net.DTO.ComunicadosCadastroDTO;
import semtd_intranet.semtd_net.DTO.ComunicadoDTO;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.service.ComunicadosService;

import java.util.List;

@RestController
@RequestMapping("/comunicados")
public class ComunicadosController {

    @Autowired
    private ComunicadosService comunicadosService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ComunicadoDTO> criarComunicado(
            @Valid @RequestBody ComunicadosCadastroDTO dto,
            @AuthenticationPrincipal Usuarios usuarioAutenticado) {

        ComunicadoDTO comunicadoDTO = comunicadosService.criarComunicado(dto, usuarioAutenticado.getRealUsername());
        return ResponseEntity.ok(comunicadoDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping
    public ResponseEntity<List<ComunicadoDTO>> listarTodos() {
        return ResponseEntity.ok(comunicadosService.listarTodosDTO());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var comunicadoOpt = comunicadosService.buscarPorIdDTO(id);
        if (comunicadoOpt.isPresent()) {
            return ResponseEntity.ok(comunicadoOpt.get());
        } else {
            return ResponseEntity.status(404).body("Comunicado com ID " + id + " não encontrado");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/titulo")
    public ResponseEntity<?> buscarPorTitulo(@RequestParam String titulo) {
        List<ComunicadoDTO> resultados = comunicadosService.buscarPorTituloParcialDTO(titulo);
        if (resultados.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum comunicado encontrado com título contendo: " + titulo);
        }
        return ResponseEntity.ok(resultados);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {
        try {
            comunicadosService.deletarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Erro ao deletar: comunicado com ID " + id + " não encontrado");
        }
    }

    // ✅ DELETE POR TÍTULO EXATO
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/titulo")
    public ResponseEntity<?> deletarPorTitulo(@RequestParam String titulo) {
        try {
            comunicadosService.deletarPorTitulo(titulo);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro ao deletar: " + e.getMessage());
        }
    }

    // ✅ PUT POR ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPorId(
            @PathVariable Long id,
            @Valid @RequestBody ComunicadosCadastroDTO dto,
            @AuthenticationPrincipal Usuarios usuarioAutenticado) {

        try {
            ComunicadoDTO atualizado = comunicadosService.atualizarPorId(id, dto, usuarioAutenticado.getRealUsername());
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ✅ PUT POR TÍTULO EXATO
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/titulo")
    public ResponseEntity<?> atualizarPorTitulo(
            @RequestParam String titulo,
            @Valid @RequestBody ComunicadosCadastroDTO dto,
            @AuthenticationPrincipal Usuarios usuarioAutenticado) {

        try {
            ComunicadoDTO atualizado = comunicadosService.atualizarPorTitulo(titulo, dto,
                    usuarioAutenticado.getRealUsername());
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/ultimomes")
    public ResponseEntity<?> comunicadosUltimoMes() {
        List<ComunicadoDTO> comunicados = comunicadosService.buscarComunicadosUltimoMes();
        if (comunicados.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum comunicado criado no último mês");
        }
        return ResponseEntity.ok(comunicados);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/ultimos2meses")
    public ResponseEntity<?> comunicadosUltimosDoisMeses() {
        List<ComunicadoDTO> comunicados = comunicadosService.buscarComunicadosUltimosDoisMeses();
        if (comunicados.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum comunicado criado nos últimos 2 meses");
        }
        return ResponseEntity.ok(comunicados);
    }

}
