package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import semtd_intranet.semtd_net.DTO.ProjetoDTO;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.model.Projeto;
import semtd_intranet.semtd_net.repository.GerenciaRepository;
import semtd_intranet.semtd_net.repository.ProjetoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private GerenciaRepository gerenciaRepository;

    private Projeto fromDTO(ProjetoDTO dto) {
        Projeto projeto = new Projeto();
        Gerencia gerencia = gerenciaRepository.findById(dto.getGerenciaId())
                .orElseThrow(() -> new RuntimeException("Gerência não encontrada."));
        projeto.setGerencia(gerencia);
        projeto.setNome(dto.getNome());
        projeto.setDescricao(dto.getDescricao());
        projeto.setStatus(dto.getStatus());
        projeto.setSmartTexto(dto.getSmartTexto());
        projeto.setLink(dto.getLink());
        projeto.setPercentualEntrega(dto.getPercentualEntrega());
        return projeto;
    }

    private ProjetoDTO toDTO(Projeto projeto) {
        return new ProjetoDTO(
                projeto.getId(),
                projeto.getGerencia().getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getStatus(),
                projeto.getSmartTexto(),
                projeto.getLink(),
                projeto.getPercentualEntrega());
    }

    public ProjetoDTO salvar(ProjetoDTO dto) {
        return toDTO(projetoRepository.save(fromDTO(dto)));
    }

    public ProjetoDTO atualizar(Long id, ProjetoDTO dto) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        Projeto atualizado = fromDTO(dto);
        atualizado.setId(projeto.getId());
        return toDTO(projetoRepository.save(atualizado));
    }

    public List<ProjetoDTO> listarTodos() {
        return projetoRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ProjetoDTO> buscarPorId(Long id) {
        return projetoRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public void deletar(Long id) {
        projetoRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return projetoRepository.existsById(id);
    }

    public Optional<ProjetoDTO> buscarPorNome(String nome) {
        return projetoRepository.findByNome(nome).map(this::toDTO);
    }

    public ProjetoDTO atualizarPorNome(String nome, ProjetoDTO dto) {
        Projeto existente = projetoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado com o nome: " + nome));
        Projeto atualizado = fromDTO(dto);
        atualizado.setId(existente.getId());
        return toDTO(projetoRepository.save(atualizado));
    }

    @Transactional
    public void deletarPorNome(String nome) {
        projetoRepository.deleteByNome(nome);
    }

    public boolean existePorNome(String nome) {
        return projetoRepository.existsByNome(nome);
    }

    public List<ProjetoDTO> listarPorGerencia(Long idGerencia) {
        return projetoRepository.findAllByGerenciaId(idGerencia).stream()
                .map(this::toDTO)
                .toList();
    }
}
