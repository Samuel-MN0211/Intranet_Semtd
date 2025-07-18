package semtd_intranet.semtd_net.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        return gerencias.stream().map(g -> toDTO(g, true)).toList();
    }

    public GerenciaDTO findById(Long id) {
        Gerencia gerencia = gerenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("A gerência não existe"));
        return toDTO(gerencia, true);
    }

    public List<GerenciaDTO> findGerenciasVinculadas(Long idExecutiva) {
        List<Gerencia> vinculadas = gerenciaRepository.findByGerenciaVinculadaId(idExecutiva);
        return vinculadas.stream().map(g -> toDTO(g, false)).toList();
    }

    public GerenciaDTO create(GerenciaDTO dto) {
        validarNomeDuplicado(dto.getNome(), null);

        Gerencia gerencia = toEntity(dto);
        if (dto.getGerenciaVinculada() != null && dto.getGerenciaVinculada().getId() != null) {
            Gerencia vinculada = buscarGerencia(dto.getGerenciaVinculada().getId());
            gerencia.setGerenciaVinculada(vinculada);
        }

        return toDTO(gerenciaRepository.save(gerencia), true);
    }

    public GerenciaDTO update(Long id, GerenciaDTO dto) {
        Gerencia existente = buscarGerencia(id);

        validarNomeDuplicado(dto.getNome(), id);

        existente.setNome(dto.getNome());
        existente.setSigla(dto.getSigla());
        existente.setDescricao(dto.getDescricao());
        existente.setObjetivos(dto.getObjetivos());
        existente.setTipoGerencia(dto.getTipoGerencia());

        if (dto.getGerenciaVinculada() != null && dto.getGerenciaVinculada().getId() != null) {
            Gerencia vinculada = buscarGerencia(dto.getGerenciaVinculada().getId());
            existente.setGerenciaVinculada(vinculada);
        } else {
            existente.setGerenciaVinculada(null);
        }

        return toDTO(gerenciaRepository.save(existente), true);
    }

    @Transactional
    public void delete(Long id) {
        Gerencia existente = buscarGerencia(id);
        gerenciaRepository.delete(existente);
    }

    public GerenciaDTO salvarComFoto(GerenciaDTO dto, MultipartFile foto) {
        validarNomeDuplicado(dto.getNome(), null);

        Gerencia gerencia = toEntity(dto);

        if (dto.getGerenciaVinculada() != null && dto.getGerenciaVinculada().getId() != null) {
            Gerencia vinculada = buscarGerencia(dto.getGerenciaVinculada().getId());
            gerencia.setGerenciaVinculada(vinculada);
        }

        if (foto != null && !foto.isEmpty()) {
            try {
                gerencia.setFotoGerencia(foto.getBytes());
            } catch (IOException e) {
                throw new IllegalArgumentException("Erro ao processar a imagem");
            }
        }

        return toDTO(gerenciaRepository.save(gerencia), true);
    }

    public GerenciaDTO atualizarComFoto(Long id, GerenciaDTO dto, MultipartFile foto) throws IOException {
        Gerencia existente = buscarGerencia(id);

        existente.setNome(dto.getNome());
        existente.setSigla(dto.getSigla());
        existente.setDescricao(dto.getDescricao());
        existente.setObjetivos(dto.getObjetivos());
        existente.setTipoGerencia(dto.getTipoGerencia());

        if (dto.getGerenciaVinculada() != null && dto.getGerenciaVinculada().getId() != null) {
            Gerencia vinculada = buscarGerencia(dto.getGerenciaVinculada().getId());
            existente.setGerenciaVinculada(vinculada);
        } else {
            existente.setGerenciaVinculada(null);
        }

        if (foto != null && !foto.isEmpty()) {
            existente.setFotoGerencia(foto.getBytes());
        }

        return toDTO(gerenciaRepository.save(existente), true);
    }

    public byte[] obterFoto(Long id) {
        Gerencia gerencia = buscarGerencia(id);
        return gerencia.getFotoGerencia();
    }

    public GerenciaDTO atualizarFoto(Long id, byte[] novaFoto) {
        Gerencia gerencia = buscarGerencia(id);
        gerencia.setFotoGerencia(novaFoto);
        return toDTO(gerenciaRepository.save(gerencia), true);
    }

    private GerenciaDTO toDTO(Gerencia entity, boolean incluirVinculadas) {
        GerenciaDTO dto = new GerenciaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setSigla(entity.getSigla());
        dto.setDescricao(entity.getDescricao());
        dto.setObjetivos(entity.getObjetivos());
        dto.setTipoGerencia(entity.getTipoGerencia());

        if (entity.getGerenciaVinculada() != null) {
            dto.setGerenciaVinculada(toDTO(entity.getGerenciaVinculada(), false));
        }

        if (incluirVinculadas && entity.getGerenciasVinculadas() != null) {
            dto.setGerenciasVinculadas(
                    entity.getGerenciasVinculadas().stream()
                            .map(g -> toDTO(g, false))
                            .collect(Collectors.toList()));
        }

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

    private Gerencia buscarGerencia(Long id) {
        return gerenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gerência não encontrada"));
    }

    private void validarNomeDuplicado(String nome, Long idAtual) {
        boolean duplicado = gerenciaRepository.findByNomeContainsIgnoreCase(nome).stream()
                .anyMatch(g -> idAtual == null || !g.getId().equals(idAtual));
        if (duplicado) {
            throw new IllegalArgumentException("Já existe uma gerência com esse nome.");
        }
    }
}
