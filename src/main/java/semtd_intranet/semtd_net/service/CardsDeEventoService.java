package semtd_intranet.semtd_net.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import semtd_intranet.semtd_net.DTO.CardsDeEventoDTO;
import semtd_intranet.semtd_net.DTO.CardsDeEventoResponseDTO;
import semtd_intranet.semtd_net.model.CardsDeEvento;
import semtd_intranet.semtd_net.repository.CardsDeEventoRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CardsDeEventoService {

    @Autowired
    private CardsDeEventoRepository repository;

    public CardsDeEventoResponseDTO salvar(CardsDeEventoDTO dto, MultipartFile foto) throws IOException {
        if (repository.existsByTitulo(dto.getTitulo())) {
            throw new IllegalArgumentException("Já existe um card com este título.");
        }

        CardsDeEvento entity = fromDTO(dto);

        if (foto != null && !foto.isEmpty()) {
            validarFoto(foto);
            entity.setFotoEvento(foto.getBytes());
        }

        return toDTO(repository.save(entity));
    }

    public CardsDeEventoResponseDTO atualizarPorId(Long id, CardsDeEventoDTO dto, MultipartFile foto)
            throws IOException {
        CardsDeEvento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card não encontrado."));

        entity.setTitulo(dto.getTitulo());
        entity.setData(dto.getData());
        entity.setHorarioInicio(dto.getHorarioInicio());
        entity.setHorarioFim(dto.getHorarioFim());
        entity.setLocalizacao(dto.getLocalizacao());
        entity.setLink(dto.getLink());

        if (foto != null && !foto.isEmpty()) {
            validarFoto(foto);
            entity.setFotoEvento(foto.getBytes());
        }

        return toDTO(repository.save(entity));
    }

    public CardsDeEventoResponseDTO atualizarFoto(Long id, byte[] novaFoto) {
        CardsDeEvento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card não encontrado."));

        entity.setFotoEvento(novaFoto);
        return toDTO(repository.save(entity));
    }

    public byte[] obterFoto(Long id) {
        return repository.findById(id)
                .map(CardsDeEvento::getFotoEvento)
                .orElse(null);
    }

    public List<CardsDeEventoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<CardsDeEventoResponseDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

    private void validarFoto(MultipartFile file) {
        String contentType = file.getContentType();
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("A imagem excede o limite de 10MB.");
        }
        if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
            throw new IllegalArgumentException("Apenas imagens JPEG ou PNG são permitidas.");
        }
    }

    private CardsDeEventoResponseDTO toDTO(CardsDeEvento entity) {
        return new CardsDeEventoResponseDTO(
                entity.getId(),
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
                dto.getLink(),
                null);
    }
}
