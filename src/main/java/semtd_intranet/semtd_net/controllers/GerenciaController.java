package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import semtd_intranet.semtd_net.DTO.GerenciaDTO;
import semtd_intranet.semtd_net.service.GerenciaService;
import java.util.List;
import org.springframework.http.*;

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
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> obterFoto(@PathVariable Long id) {
        byte[] foto = gerenciaService.obterFoto(id);
        if (foto == null)
            return ResponseEntity.notFound().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/vinculadas/{idGerenciaExecutiva}")
    public ResponseEntity<List<GerenciaDTO>> getGerenciasVinculadas(@PathVariable Long idGerenciaExecutiva) {
        return ResponseEntity.ok(gerenciaService.findGerenciasVinculadas(idGerenciaExecutiva));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cadastrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cadastrarGerencia(
            @RequestPart("dto") @Valid GerenciaDTO dto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            return ResponseEntity.ok(gerenciaService.salvarComFoto(dto, foto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/editar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarGerenciaComFoto(
            @PathVariable Long id,
            @RequestPart("dto") @Valid GerenciaDTO dto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            return ResponseEntity.ok(gerenciaService.atualizarComFoto(id, dto, foto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/foto")
    public ResponseEntity<?> atualizarFoto(
            @PathVariable Long id,
            @RequestPart("foto") MultipartFile file) {
        try {
            String nome = file.getOriginalFilename().toLowerCase();
            if (!(nome.endsWith(".jpg") || nome.endsWith(".jpeg") || nome.endsWith(".png"))) {
                return ResponseEntity.badRequest().body("Apenas imagens JPEG ou PNG são permitidas.");
            }

            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("A imagem excede o limite de 10MB.");
            }

            return ResponseEntity.ok(gerenciaService.atualizarFoto(id, file.getBytes()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar imagem.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteGerencia(@PathVariable Long id) {
        gerenciaService.delete(id);
        return ResponseEntity.ok("Gerência deletada com sucesso.");
    }
}
