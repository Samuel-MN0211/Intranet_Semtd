package semtd_intranet.semtd_net.service;

import semtd_intranet.semtd_net.DTO.CardsBaseDTO;
import semtd_intranet.semtd_net.model.CardsBase;
import semtd_intranet.semtd_net.repository.CardsBaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CardsBaseService {

    @Autowired
    private CardsBaseRepository repository;

    private CardsBase fromDTO(CardsBaseDTO dto) {
        CardsBase s = new CardsBase();
        // s.setId(dto.getId());
        s.setNome(dto.getNome());
        s.setDescricao(dto.getDescricao());
        s.setLink(dto.getLink());
        s.setIcone(dto.getIcone());
        s.setCategoria(dto.getCategoria());
        return s;
    }

    private CardsBaseDTO toDTO(CardsBase sistema) {
        CardsBaseDTO dto = new CardsBaseDTO();
        dto.setId(sistema.getId());
        dto.setNome(sistema.getNome());
        dto.setDescricao(sistema.getDescricao());
        dto.setLink(sistema.getLink());
        dto.setIcone(sistema.getIcone());
        dto.setCategoria(sistema.getCategoria());
        return dto;
    }

    public CardsBaseDTO salvar(CardsBaseDTO dto) {
        CardsBase sistema = fromDTO(dto);
        if (repository.existsByNome(sistema.getNome())) {
            throw new RuntimeException("Já existe um Card com o nome '" + sistema.getNome() + "'.");
        }
        return toDTO(repository.save(sistema));
    }

    public CardsBaseDTO atualizarPorId(Long id, CardsBaseDTO dto) {
        Optional<CardsBase> optSistema = repository.findById(id);
        if (optSistema.isEmpty()) {
            throw new RuntimeException("Card não encontrado.");
        }
        CardsBase s = optSistema.get();
        s.setNome(dto.getNome());
        s.setDescricao(dto.getDescricao());
        s.setLink(dto.getLink());
        s.setIcone(dto.getIcone());
        s.setCategoria(dto.getCategoria());
        return toDTO(repository.save(s));
    }

    public CardsBaseDTO atualizarPorNome(String nome, CardsBaseDTO dto) {
        Optional<CardsBase> optSistema = repository.findByNome(nome);
        if (optSistema.isEmpty()) {
            throw new RuntimeException("Card não encontrado.");
        }
        CardsBase s = optSistema.get();
        s.setNome(dto.getNome());
        s.setDescricao(dto.getDescricao());
        s.setLink(dto.getLink());
        s.setIcone(dto.getIcone());
        s.setCategoria(dto.getCategoria());
        return toDTO(repository.save(s));
    }

    public List<CardsBaseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<CardsBaseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Optional<CardsBaseDTO> buscarPorNome(String nome) {
        return repository.findByNome(nome).map(this::toDTO);
    }

    public List<CardsBaseDTO> buscarPorFragmentoNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::toDTO)
                .toList();
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
}
