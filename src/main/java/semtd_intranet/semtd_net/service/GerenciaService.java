package semtd_intranet.semtd_net.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import semtd_intranet.semtd_net.DTO.GerenciaDTO;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.repository.GerenciaRepository;

@Service
public class GerenciaService {

    @Autowired
    private GerenciaRepository gerenciaRepository;

    public List<GerenciaDTO> findAll() {
        List<Gerencia> gerencias = gerenciaRepository.findAll();
        if (gerencias.isEmpty()) {
            throw new EntityNotFoundException("Não existe nenhuma gerência cadastrada");
        }
        return gerencias.stream().map(this::toDTO).toList();
    }

    public GerenciaDTO findById(Long id) {
        Gerencia gerencia = gerenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("A gerência não existe"));
        return toDTO(gerencia);
    }

    public GerenciaDTO create(GerenciaDTO dto) {
        boolean nomeJaExiste = !gerenciaRepository.findByNomeContainsIgnoreCase(dto.getNome()).isEmpty();

        if (nomeJaExiste) {
            throw new IllegalArgumentException("Já existe uma gerência com esse nome.");
        }

        Gerencia novaGerencia = toEntity(dto);
        Gerencia salva = gerenciaRepository.save(novaGerencia);
        return toDTO(salva);
    }

    public GerenciaDTO update(Long id, GerenciaDTO dto) {
        Gerencia existente = gerenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("A gerência não existe"));

        boolean nomeDuplicado = gerenciaRepository.findByNomeContainsIgnoreCase(dto.getNome())
                .stream()
                .anyMatch(g -> !g.getId().equals(id));
        if (nomeDuplicado) {
            throw new IllegalArgumentException("Já existe outra gerência com esse nome.");
        }

        Gerencia atualizada = toEntity(dto);
        atualizada.setId(existente.getId());
        Gerencia salva = gerenciaRepository.save(atualizada);
        return toDTO(salva);
    }

    @Transactional
    public void delete(Long id) {
        Gerencia existente = gerenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("A gerência não existe"));
        gerenciaRepository.delete(existente);
    }

    private GerenciaDTO toDTO(Gerencia entity) {
        GerenciaDTO dto = new GerenciaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setSigla(entity.getSigla());
        dto.setDescricao(entity.getDescricao());
        dto.setObjetivos(entity.getObjetivos());
        dto.setTipoGerencia(entity.getTipoGerencia());
        return dto;
    }

    private Gerencia toEntity(GerenciaDTO dto) {
        Gerencia entity = new Gerencia();
        entity.setNome(dto.getNome());
        entity.setSigla(dto.getSigla());
        entity.setDescricao(dto.getDescricao());
        entity.setObjetivos(dto.getObjetivos());
        entity.setTipoGerencia(dto.getTipoGerencia());
        return entity;
    }
}