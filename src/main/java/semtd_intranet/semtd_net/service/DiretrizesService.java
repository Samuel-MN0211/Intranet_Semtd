package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import semtd_intranet.semtd_net.DTO.DiretrizesDTO;
import semtd_intranet.semtd_net.model.Diretrizes;
import semtd_intranet.semtd_net.repository.DiretrizesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiretrizesService {

    @Autowired
    private DiretrizesRepository diretrizesRepository;

    private Diretrizes fromDTO(DiretrizesDTO dto) {
        Diretrizes d = new Diretrizes();
        d.setTitulo(dto.getTitulo());
        d.setDescricao(dto.getDescricao());
        return d;
    }

    private DiretrizesDTO toDTO(Diretrizes diretriz) {
        DiretrizesDTO dto = new DiretrizesDTO();
        dto.setId(diretriz.getId());
        dto.setTitulo(diretriz.getTitulo());
        dto.setDescricao(diretriz.getDescricao());
        return dto;
    }

    public DiretrizesDTO salvar(DiretrizesDTO dto) {
        Diretrizes diretriz = fromDTO(dto);
        if (diretrizesRepository.existsByTitulo(diretriz.getTitulo())) {
            throw new RuntimeException("Já existe uma diretriz com o título '" + diretriz.getTitulo() + "'.");
        }
        return toDTO(diretrizesRepository.save(diretriz));
    }

    public DiretrizesDTO atualizarPorId(Long id, DiretrizesDTO dto) {
        Optional<Diretrizes> opt = diretrizesRepository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Diretriz não encontrada.");
        }
        Diretrizes d = opt.get();
        d.setTitulo(dto.getTitulo());
        d.setDescricao(dto.getDescricao());
        return toDTO(diretrizesRepository.save(d));
    }

    public DiretrizesDTO atualizarPorTitulo(String titulo, DiretrizesDTO dto) {
        Optional<Diretrizes> opt = diretrizesRepository.findByTitulo(titulo);
        if (opt.isEmpty()) {
            throw new RuntimeException("Diretriz não encontrada.");
        }
        Diretrizes d = opt.get();
        d.setTitulo(dto.getTitulo());
        d.setDescricao(dto.getDescricao());
        return toDTO(diretrizesRepository.save(d));
    }

    public List<DiretrizesDTO> listarTodas() {
        return diretrizesRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<DiretrizesDTO> buscarPorId(Long id) {
        return diretrizesRepository.findById(id).map(this::toDTO);
    }

    public Optional<DiretrizesDTO> buscarPorTitulo(String titulo) {
        return diretrizesRepository.findByTitulo(titulo).map(this::toDTO);
    }

    public boolean existePorId(Long id) {
        return diretrizesRepository.existsById(id);
    }

    public boolean existePorTitulo(String titulo) {
        return diretrizesRepository.existsByTitulo(titulo);
    }

    @Transactional
    public void deletarPorId(Long id) {
        diretrizesRepository.deleteById(id);
    }

    @Transactional
    public void deletarPorTitulo(String titulo) {
        diretrizesRepository.deleteByTitulo(titulo);
    }
}
