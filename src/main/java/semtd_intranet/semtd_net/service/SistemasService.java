package semtd_intranet.semtd_net.service;

import semtd_intranet.semtd_net.DTO.SistemasDTO;
import semtd_intranet.semtd_net.model.Sistemas;
import semtd_intranet.semtd_net.repository.SistemasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SistemasService {

    @Autowired
    private SistemasRepository repository;

    private Sistemas fromDTO(SistemasDTO dto) {
        Sistemas s = new Sistemas();
        s.setNome(dto.getNome());
        s.setDescricao(dto.getDescricao());
        s.setLink(dto.getLink());
        return s;
    }

    public SistemasDTO salvar(SistemasDTO dto) {
        Sistemas sistema = fromDTO(dto);
        if (repository.existsByNome(sistema.getNome())) {
            throw new RuntimeException("Já existe um sistema com o nome '" + sistema.getNome() + "'.");
        }
        return toDTO(repository.save(sistema));
    }

    public SistemasDTO atualizarPorId(Long id, SistemasDTO dto) {
        Optional<Sistemas> optSistema = repository.findById(id);
        if (optSistema.isEmpty()) {
            throw new RuntimeException("Sistema não encontrado.");
        }
        Sistemas s = optSistema.get();
        s.setNome(dto.getNome());
        s.setDescricao(dto.getDescricao());
        s.setLink(dto.getLink());
        return toDTO(repository.save(s));
    }

    public SistemasDTO atualizarPorNome(String nome, SistemasDTO dto) {
        Optional<Sistemas> optSistema = repository.findByNome(nome);
        if (optSistema.isEmpty()) {
            throw new RuntimeException("Sistema não encontrado.");
        }
        Sistemas s = optSistema.get();
        s.setNome(dto.getNome());
        s.setDescricao(dto.getDescricao());
        s.setLink(dto.getLink());
        return toDTO(repository.save(s));
    }

    public List<SistemasDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<SistemasDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Optional<SistemasDTO> buscarPorNome(String nome) {
        return repository.findByNome(nome).map(this::toDTO);
    }

    @Transactional
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deletarPorNome(String nome) {
        repository.deleteByNome(nome);
    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }

    public boolean existePorNome(String nome) {
        return repository.existsByNome(nome);
    }

    private SistemasDTO toDTO(Sistemas sistema) {
        SistemasDTO dto = new SistemasDTO();
        dto.setId(sistema.getId());
        dto.setNome(sistema.getNome());
        dto.setDescricao(sistema.getDescricao());
        dto.setLink(sistema.getLink());
        return dto;
    }

}
