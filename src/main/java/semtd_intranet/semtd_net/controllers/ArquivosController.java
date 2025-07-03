package semtd_intranet.semtd_net.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import semtd_intranet.semtd_net.DTO.ArquivoDTO;
import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.ArquivosRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;
import semtd_intranet.semtd_net.security.JwtUtil;
import semtd_intranet.semtd_net.service.ArquivosService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/arquivos")
public class ArquivosController {

    @Autowired
    private ArquivosService arquivosService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ArquivosRepository arquivosRepository;

    private ArquivoDTO toDTO(Arquivos arq) {
        return new ArquivoDTO(arq.getId(), arq.getNomeArquivo(), "/semtd/arquivos/foto/" + arq.getId());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PostMapping("/upload-usuario")
    public ResponseEntity<?> uploadComUsuario(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {
        String email = jwtUtil.getEmailFromRequest(request);
        Arquivos arquivo = arquivosService.salvarComUsuario(file, email);
        return ResponseEntity.ok(toDTO(arquivo));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload-livre")
    public ResponseEntity<?> uploadSemUsuario(@RequestParam("file") MultipartFile file) throws IOException {
        Arquivos arquivo = arquivosService.salvarSemUsuario(file);
        return ResponseEntity.ok(toDTO(arquivo));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/minha-foto")
    public ResponseEntity<?> minhaFoto(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findByEmail(userDetails.getUsername());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Usuarios usuario = usuarioOpt.get();
        Arquivos foto = usuario.getFotoUsuario();

        if (foto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não possui nenhuma foto.");
        }

        return ResponseEntity.ok(toDTO(foto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/sem-usuario")
    public ResponseEntity<?> listarSemUsuario() {
        List<Arquivos> lista = arquivosService.listarFotosSemUsuario();

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma foto sem associação foi encontrada.");
        }

        List<ArquivoDTO> resposta = lista.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/por-gerencia")
    public ResponseEntity<?> listarFotosGerentes(@RequestParam Long gerenciaId) {
        List<Arquivos> lista = arquivosService.listarFotosPorGerenciaComCargoGerente(gerenciaId);

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhuma foto encontrada para gerentes desta gerência.");
        }

        List<ArquivoDTO> resposta = lista.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> obterFotoPorId(@PathVariable Long id) {
        Arquivos arquivo = arquivosRepository.findById(id).orElse(null);

        if (arquivo == null || arquivo.getDados() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        MediaType tipo;
        String nome = arquivo.getNomeArquivo().toLowerCase();

        if (nome.endsWith(".png")) {
            tipo = MediaType.IMAGE_PNG;
        } else if (nome.endsWith(".jpg") || nome.endsWith(".jpeg")) {
            tipo = MediaType.IMAGE_JPEG;
        } else {
            tipo = MediaType.APPLICATION_OCTET_STREAM;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(tipo);
        return new ResponseEntity<>(arquivo.getDados(), headers, HttpStatus.OK);
    }
}
