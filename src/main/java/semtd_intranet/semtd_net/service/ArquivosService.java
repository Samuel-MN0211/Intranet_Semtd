package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import semtd_intranet.semtd_net.DTO.ArquivosDTO;
import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.repository.ArquivosRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ArquivosService {

    @Value("${storage.local.path}")
    private String storagePath;

    @Autowired
    private ArquivosRepository arquivosRepository;

    public List<Arquivos> listarTodos() {
        return arquivosRepository.findAll();
    }

    public Optional<Arquivos> buscarPorId(Long id) {
        return arquivosRepository.findById(id);
    }

    public Arquivos salvarArquivo(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Arquivo está vazio.");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IOException("Arquivo excede 10MB.");
        }

        File dir = new File(storagePath);
        if (!dir.exists())
            dir.mkdirs();

        Path path = Paths.get(storagePath, file.getOriginalFilename());
        Files.write(path, file.getBytes());

        Arquivos arquivo = new Arquivos();
        arquivo.setNomeArquivo(file.getOriginalFilename());
        arquivo.setCaminhoArquivo(path.toString());

        return arquivosRepository.save(arquivo);
    }

    public boolean deletarPorId(Long id) {
        Optional<Arquivos> opt = arquivosRepository.findById(id);
        if (opt.isEmpty())
            return false;

        deletarFisicamente(opt.get().getCaminhoArquivo());
        arquivosRepository.deleteById(id);
        return true;
    }

    public boolean deletarPorNome(String nome) {
        List<Arquivos> arquivos = arquivosRepository.findAllByNomeArquivo(nome);
        if (arquivos.isEmpty())
            return false;

        for (Arquivos arquivo : arquivos) {
            deletarFisicamente(arquivo.getCaminhoArquivo());
        }

        arquivosRepository.deleteAll(arquivos);
        return true;
    }

    private void deletarFisicamente(String caminho) {
        try {
            Files.deleteIfExists(Paths.get(caminho));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<ByteArrayResource> obterPdfPorId(Long id) throws IOException {
        Optional<Arquivos> opt = arquivosRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();

        return retornarArquivoComoPdf(opt.get());
    }

    public List<ArquivosDTO> buscarMetadadosPorNome(String nome) {
        List<Arquivos> arquivos = arquivosRepository.findAllByNomeArquivo(nome);
        return arquivos.stream()
                .map(a -> new ArquivosDTO(
                        a.getId(),
                        a.getNomeArquivo(),
                        "/semtd/arquivos/" + a.getId()))
                .toList();
    }

    public ResponseEntity<ByteArrayResource> retornarArquivoComoPdf(Arquivos arquivo) throws IOException {
        Path path = Paths.get(arquivo.getCaminhoArquivo());
        if (!Files.exists(path))
            return ResponseEntity.notFound().build();

        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + arquivo.getNomeArquivo() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(data.length)
                .body(resource);
    }

    // lista crua de arquivos
    public List<Arquivos> buscarPorNome(String nome) {
        return arquivosRepository.findAllByNomeArquivo(nome);
    }
}
