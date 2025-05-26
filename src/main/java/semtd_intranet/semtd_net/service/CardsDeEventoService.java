package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import semtd_intranet.semtd_net.DTO.CardsDeEventoDTO;
import semtd_intranet.semtd_net.model.CardsDeEvento;
import semtd_intranet.semtd_net.repository.CardsDeEventoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardsDeEventoService {

    @Autowired
    private CardsDeEventoRepository repository;

    private CardsDeEventoDTO toDTO(CardsDeEvento entity) {
        return new CardsDeEventoDTO(
                entity.getTitulo(),
                entity.getData(),
                entity.getHorarioInicio(),
                entity.getHorarioFim(),
                entity.getLocalizacao(),
                entity.getLink());
    }

    private CardsDeEvento fromDTO(CardsDeEventoDTO dto) {
        return new CardsDeEvento(
                null,
                dto.getTitulo(),
                dto.getData(),
                dto.getHorarioInicio(),
                dto.getHorarioFim(),
                dto.getLocalizacao(),
                dto.getLink());
    }

    public CardsDeEventoDTO salvar(CardsDeEventoDTO dto) {
        if (repository.existsByTitulo(dto.getTitulo())) {
            throw new IllegalArgumentException("Já existe um card com este título.");
        }
        CardsDeEvento salvo = repository.save(fromDTO(dto));
        return toDTO(salvo);
    }

    public CardsDeEventoDTO atualizarPorId(Long id, CardsDeEventoDTO dto) {
        CardsDeEvento entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Card não encontrado."));
        entity.setTitulo(dto.getTitulo());
        entity.setData(dto.getData());
        entity.setHorarioInicio(dto.getHorarioInicio());
        entity.setHorarioFim(dto.getHorarioFim());
        entity.setLocalizacao(dto.getLocalizacao());
        entity.setLink(dto.getLink());
        return toDTO(repository.save(entity));
    }

    public CardsDeEventoDTO atualizarPorTitulo(String titulo, CardsDeEventoDTO dto) {
        CardsDeEvento entity = repository.findByTitulo(titulo)
                .orElseThrow(() -> new RuntimeException("Card não encontrado."));
        entity.setTitulo(dto.getTitulo());
        entity.setData(dto.getData());
        entity.setHorarioInicio(dto.getHorarioInicio());
        entity.setHorarioFim(dto.getHorarioFim());
        entity.setLocalizacao(dto.getLocalizacao());
        entity.setLink(dto.getLink());
        return toDTO(repository.save(entity));
    }

    public List<CardsDeEventoDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<CardsDeEventoDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Optional<CardsDeEventoDTO> buscarPorTitulo(String titulo) {
        return repository.findByTitulo(titulo).map(this::toDTO);
    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }

    public boolean existePorTitulo(String titulo) {
        return repository.existsByTitulo(titulo);
    }

    @Transactional
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deletarPorTitulo(String titulo) {
        repository.deleteByTitulo(titulo);
    }
}
