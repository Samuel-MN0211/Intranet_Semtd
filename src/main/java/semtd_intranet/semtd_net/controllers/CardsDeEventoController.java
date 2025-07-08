package semtd_intranet.semtd_net.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import semtd_intranet.semtd_net.DTO.CardsDeEventoDTO;
import semtd_intranet.semtd_net.DTO.CardsDeEventoResponseDTO;
import semtd_intranet.semtd_net.service.CardsDeEventoService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cards-evento")
public class CardsDeEventoController {

    @Autowired
    private CardsDeEventoService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cadastrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criar(
            @RequestPart("dto") @Valid CardsDeEventoDTO dto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            return ResponseEntity.ok(service.salvar(dto, foto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao processar a imagem.");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<CardsDeEventoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<CardsDeEventoResponseDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        var foto = service.obterFoto(id);
        if (foto == null)
            return ResponseEntity.notFound().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Ajustável
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarPorId(
            @PathVariable Long id,
            @RequestPart("dto") @Valid CardsDeEventoDTO dto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            return ResponseEntity.ok(service.atualizarPorId(id, dto, foto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao processar a imagem.");
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

            byte[] imagem = file.getBytes();
            return ResponseEntity.ok(service.atualizarFoto(id, imagem));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao processar a imagem.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPorId(@PathVariable Long id) {
        if (!service.existePorId(id))
            return ResponseEntity.notFound().build();
        service.deletarPorId(id);
        return ResponseEntity.ok("Card deletado com sucesso.");
    }
}
