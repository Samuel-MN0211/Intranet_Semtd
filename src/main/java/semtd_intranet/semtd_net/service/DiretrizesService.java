package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semtd_intranet.semtd_net.model.Diretrizes;
import semtd_intranet.semtd_net.repository.DiretrizesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiretrizesService {

    @Autowired
    private DiretrizesRepository diretrizesRepository;

    public Diretrizes salvar(Diretrizes diretriz) {
        return diretrizesRepository.save(diretriz);
    }

    public List<Diretrizes> listarTodas() {
        return diretrizesRepository.findAll();
    }

    public Optional<Diretrizes> buscarPorId(Long id) {
        return diretrizesRepository.findById(id);
    }

    public Optional<Diretrizes> buscarPorTitulo(String titulo) {
        return diretrizesRepository.findByTitulo(titulo);
    }

    public boolean existePorId(Long id) {
        return diretrizesRepository.existsById(id);
    }

    public boolean existePorTitulo(String titulo) {
        return diretrizesRepository.existsByTitulo(titulo);
    }

    public void deletarPorId(Long id) {
        diretrizesRepository.deleteById(id);
    }

    public void deletarPorTitulo(String titulo) {
        diretrizesRepository.deleteByTitulo(titulo);
    }
}
