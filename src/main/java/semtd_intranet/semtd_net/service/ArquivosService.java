package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import semtd_intranet.semtd_net.model.Arquivos;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.ArquivosRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ArquivosService {

    @Autowired
    private ArquivosRepository arquivosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public Arquivos salvarSemUsuario(MultipartFile file) throws IOException {
        Arquivos arquivo = new Arquivos();
        arquivo.setNomeArquivo(file.getOriginalFilename());
        arquivo.setDados(file.getBytes());
        return arquivosRepository.save(arquivo);
    }

    public Arquivos salvarComUsuario(MultipartFile file, String email) throws IOException {
        Usuarios usuario = usuariosRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Arquivos fotoAntiga = usuario.getFotoUsuario();
        if (fotoAntiga != null) { // Se usuario ja tiver uma foto ->
            usuario.setFotoUsuario(null);
            usuariosRepository.save(usuario);
            arquivosRepository.deleteById(fotoAntiga.getId());
        }

        Arquivos novoArquivo = new Arquivos();
        novoArquivo.setNomeArquivo(file.getOriginalFilename());
        novoArquivo.setDados(file.getBytes());
        Arquivos salvo = arquivosRepository.save(novoArquivo);

        usuario.setFotoUsuario(salvo);
        usuariosRepository.save(usuario);

        return salvo;
    }

    public Arquivos buscarFotoDoUsuario(String email) {
        return arquivosRepository.findByUsuario_Email(email)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));
    }

    public List<Arquivos> listarFotosSemUsuario() {
        return arquivosRepository.findByUsuarioIsNull();
    }

    public List<Arquivos> listarFotosPorGerenciaComCargoGerente(Long gerenciaId) {
        return arquivosRepository.findByGerenciaAndCargo(gerenciaId);
    }
}
