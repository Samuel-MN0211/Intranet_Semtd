package semtd_intranet.semtd_net.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import semtd_intranet.semtd_net.DTO.ArquivosDTO;
import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.service.ArquivosService;

@RestController
@RequestMapping("/arquivos")
public class ArquivosController {

    @Autowired
    private ArquivosService arquivosService;

    // GET PARA RETORNAR TODOS OS ARQUIVOS COMO METADADOS + LINKS DIRECIONAIS PARA
    // TODAS ASINSTÂNCIAS (RETORNAR TODOS OS BINÁRIOS VAI SOBRECARREGAR A RESPOSTA)
    // OS LINKS ESTÃO DIRECIONADOS PARA O ENDPOINT GET POR ID
    // GET PARA RETORNAR TODOS OS ARQUIVOS COMO METADADOS (ID, nome e link para o
    // PDF COM ID DE PARAMETRO)
    // Isso evita sobrecarregar com binários e permite navegação a cada arquivo
    // individual
    @GetMapping
    public ResponseEntity<List<ArquivosDTO>> listarTodosComMetadados() {
        List<Arquivos> arquivos = arquivosService.listarTodos();

        // Mapeia cada arquivo para um DTO contendo metadados + link
        List<ArquivosDTO> arquivosDTO = arquivos.stream()
                .map(a -> new ArquivosDTO(
                        a.getId(),
                        a.getNomeArquivo(),
                        "/semtd/arquivos/" + a.getId())) // link direto ao PDF
                .toList();

        return ResponseEntity.ok(arquivosDTO);
    }

    // GET PARA RETORNAR UM ARQUIVO COM ID COMO PARÂMETRO. RETORNA DIRETAMENTE O PDF
    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> obterArquivoPorId(@PathVariable Long id) {
        try {
            return arquivosService.obterPdfPorId(id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET PARA RETORNAR UM ARQUIVO COM NOME COMO PARÂMETRO. ESPERA-SE QUE SEJA
    // APENAS APLICÁVEL DURANTE TROUBLESHOOTING NO DESENVOLVIMENTO
    @GetMapping("/buscar")
    public ResponseEntity<?> obterArquivosPorNome(@RequestParam String nome) {
        List<Arquivos> arquivos = arquivosService.buscarPorNome(nome);

        if (arquivos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (arquivos.size() == 1) {
            try {
                return arquivosService.retornarArquivoComoPdf(arquivos.get(0));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao ler o arquivo.");
            }
        }

        List<ArquivosDTO> arquivosDTO = arquivos.stream()
                .map(ArquivosDTO::new)
                .toList();

        return ResponseEntity.ok(arquivosDTO);
    }

    // POST PARA CADASTRO DE NOVO ARQUIVO
    @PostMapping("/upload")
    public ResponseEntity<?> uploadArquivo(@RequestParam("arquivo") MultipartFile file) {
        final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB em bytes

        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("O arquivo excede o tamanho máximo permitido de 10MB.");
        }

        try {
            Arquivos salvo = arquivosService.salvarArquivo(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar o arquivo.");
        }
    }

    // DELETE PARA DELETAR UM ARQUIVO COM ID COMO PARÂMETRO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        boolean removido = arquivosService.deletarPorId(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // DELETE PARA DELETAR UM ARQUIVO COM NOME COMO PARÂMETRO
    @DeleteMapping
    public ResponseEntity<Void> deletarPorNome(@RequestParam String nome) {
        boolean removido = arquivosService.deletarPorNome(nome);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
