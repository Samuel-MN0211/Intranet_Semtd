package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import semtd_intranet.semtd_net.DTO.ComunicadoDTO;
import semtd_intranet.semtd_net.DTO.ComunicadosCadastroDTO;
import semtd_intranet.semtd_net.model.Comunicados;
import semtd_intranet.semtd_net.model.Usuarios;
import semtd_intranet.semtd_net.repository.ComunicadosRepository;
import semtd_intranet.semtd_net.repository.UsuariosRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class ComunicadosService {

    @Autowired
    private ComunicadosRepository comunicadosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public ComunicadoDTO criarComunicado(ComunicadosCadastroDTO dto, String realUsername) {
        Usuarios criador = usuariosRepository.findByRealUsername(realUsername)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Comunicados comunicado = new Comunicados();
        comunicado.setTitulo(dto.getTitulo());
        comunicado.setConteudo(dto.getConteudo());
        comunicado.setExpiraEm(dto.getExpiraEm());
        comunicado.setCriadoPor(criador);

        Comunicados salvo = comunicadosRepository.save(comunicado);
        return mapToDTO(salvo);
    }

    public List<ComunicadoDTO> listarTodosDTO() {
        return comunicadosRepository.findAll()
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ComunicadoDTO> buscarPorIdDTO(Long id) {
        return comunicadosRepository.findById(id).map(this::mapToDTO);
    }

    public List<ComunicadoDTO> buscarPorTituloParcialDTO(String titulo) {
        return comunicadosRepository.findAllByTituloContainingIgnoreCase(titulo)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!comunicadosRepository.existsById(id)) {
            throw new RuntimeException("Comunicado com ID " + id + " não encontrado");
        }
        comunicadosRepository.deleteById(id);
    }

    @Transactional
    public void deletarPorTitulo(String titulo) {
        Optional<Comunicados> comunicadoOpt = comunicadosRepository.findByTituloIgnoreCase(titulo);
        if (comunicadoOpt.isEmpty()) {
            throw new RuntimeException("Comunicado com título \"" + titulo + "\" não encontrado");
        }
        comunicadosRepository.delete(comunicadoOpt.get());
    }

    @Transactional
    public ComunicadoDTO atualizarPorId(Long id, ComunicadosCadastroDTO dto, String realUsername) {
        Comunicados comunicado = comunicadosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunicado com ID " + id + " não encontrado"));

        Usuarios autor = usuariosRepository.findByRealUsername(realUsername)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        comunicado.setTitulo(dto.getTitulo());
        comunicado.setConteudo(dto.getConteudo());
        comunicado.setExpiraEm(dto.getExpiraEm());
        comunicado.setCriadoPor(autor);

        return mapToDTO(comunicadosRepository.save(comunicado));
    }

    @Transactional
    public ComunicadoDTO atualizarPorTitulo(String titulo, ComunicadosCadastroDTO dto, String realUsername) {
        Comunicados comunicado = comunicadosRepository.findByTituloIgnoreCase(titulo)
                .orElseThrow(() -> new RuntimeException("Comunicado com título \"" + titulo + "\" não encontrado"));

        Usuarios autor = usuariosRepository.findByRealUsername(realUsername)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        comunicado.setTitulo(dto.getTitulo());
        comunicado.setConteudo(dto.getConteudo());
        comunicado.setExpiraEm(dto.getExpiraEm());
        comunicado.setCriadoPor(autor);

        return mapToDTO(comunicadosRepository.save(comunicado));
    }

    public List<ComunicadoDTO> buscarComunicadosUltimoMes() {
        LocalDateTime umMesAtras = LocalDateTime.now().minusMonths(1);
        return comunicadosRepository.findByCriadoEmAfter(umMesAtras)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ComunicadoDTO> buscarComunicadosUltimosDoisMeses() {
        LocalDateTime doisMesesAtras = LocalDateTime.now().minusMonths(2);
        return comunicadosRepository.findByCriadoEmAfter(doisMesesAtras)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ComunicadoDTO mapToDTO(Comunicados comunicado) {
        return new ComunicadoDTO(
                comunicado.getId(),
                comunicado.getTitulo(),
                comunicado.getConteudo(),
                comunicado.getCriadoPor().getRealUsername(),
                comunicado.getCriadoEm(),
                comunicado.getExpiraEm(),
                comunicado.getAtualizadoEm());
    }
}