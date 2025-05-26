package semtd_intranet.semtd_net.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.ArquivosRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArquivosService {

    @Value("${storage.local.path}")
    private String storagePath;

    @Autowired
    private ArquivosRepository arquivosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public ResponseEntity<?> obterFotoDoUsuarioLogado() {
        Usuarios usuario = getUsuarioAutenticado();
        Arquivos foto = usuario.getFotoUsuario();
        if (foto == null) {
            return ResponseEntity.notFound().build();
        }

        return retornarArquivo(foto);
    }

    public ResponseEntity<?> salvarOuAtualizarFotoDoUsuarioLogado(MultipartFile file) {
        Usuarios usuario = getUsuarioAutenticado();

        // Deleta a antiga, se existir
        Arquivos fotoAntiga = usuario.getFotoUsuario();
        if (fotoAntiga != null) {
            Path path = Paths.get(storagePath, fotoAntiga.getNomeArquivo());
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar foto antiga.");
            }
            arquivosRepository.deleteById(fotoAntiga.getId());
        }

        try {
            Arquivos novaFoto = salvarArquivo(file);
            usuario.setFotoUsuario(novaFoto);
            usuariosRepository.save(usuario);
            return ResponseEntity.ok("Foto atualizada com sucesso");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar nova foto.");
        }
    }

    public ResponseEntity<?> deletarFotoDoUsuarioLogado() {
        Usuarios usuario = getUsuarioAutenticado();
        Arquivos foto = usuario.getFotoUsuario();
        if (foto == null)
            return ResponseEntity.notFound().build();

        arquivosRepository.deleteById(foto.getId());
        usuario.setFotoUsuario(null);
        usuariosRepository.save(usuario);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> retornarArquivo(Arquivos arquivo) {
        ByteArrayResource resource = new ByteArrayResource(arquivo.getDados());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getNomeArquivo() + "\"")
                .contentLength(arquivo.getDados().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private Usuarios getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuariosRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"));
    }

    public Arquivos salvarArquivo(MultipartFile file) throws IOException {
        String nomeOriginal = file.getOriginalFilename();
        byte[] dados = file.getBytes();

        Arquivos arquivo = new Arquivos();
        arquivo.setNomeArquivo(nomeOriginal);
        arquivo.setDados(dados);

        return arquivosRepository.save(arquivo);
    }

}
