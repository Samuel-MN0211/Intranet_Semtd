package semtd_intranet.semtd_net.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import semtd_intranet.semtd_net.DTO.GerenciaDTO;
import semtd_intranet.semtd_net.model.Gerencia;
import semtd_intranet.semtd_net.repository.GerenciaRepository;

@Component
public class GerenciaService {

    @Autowired
    private GerenciaRepository gerenciaRepository;


    public List<Gerencia> findAll() {
        List<Gerencia> gerencias = gerenciaRepository.findAll();
        if (gerencias.isEmpty()) {
            throw new EntityNotFoundException("Não existe nenhuma gerência cadastrada");
        }
        return gerencias;
    }

    public Gerencia findById(Long id) {
        return gerenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("A gerência não existe"));
    }


    public Gerencia create(GerenciaDTO dto) {
        boolean nomeJaExiste = !gerenciaRepository.findByNomeContainsIgnoreCase(dto.getNome()).isEmpty();

        if (nomeJaExiste) {
            throw new IllegalArgumentException("Já existe uma gerência com esse nome.");
        }

        return gerenciaRepository.save(dto.toEntity());

    }

    public Gerencia update(Long id, GerenciaDTO dto) {
        Gerencia existente = findById(id);

        // Verifica se existe outra gerência com o mesmo nome, diferente dessa id
        boolean nomeDuplicado = gerenciaRepository.findByNomeContainsIgnoreCase(dto.getNome())
                .stream()
                .anyMatch(g -> !g.getId().equals(id));
        if (nomeDuplicado) {
            throw new IllegalArgumentException("Já existe outra gerência com esse nome.");
        }

        Gerencia atualizada = dto.toEntity();
        atualizada.setId(existente.getId());
        return gerenciaRepository.save(atualizada);
    }

    public void delete(Long id) {
        Gerencia existente = findById(id);
        gerenciaRepository.delete(existente);
        return gerenciaRepository.findById(id).orElse(null);
    }

    @Override
    public Gerencia save(Gerencia t) {
        return gerenciaRepository.save(t);
    }

}
