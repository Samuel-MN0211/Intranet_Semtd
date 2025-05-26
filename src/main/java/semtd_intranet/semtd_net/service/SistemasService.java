package semtd_intranet.semtd_net.service;

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

    public Sistemas salvar(Sistemas sistema) {
        if (repository.existsByNome(sistema.getNome())) {
            throw new RuntimeException("Já existe um sistema com o nome '" + sistema.getNome() + "'.");
        }
        return repository.save(sistema);
    }

    public List<Sistemas> listarTodos() {
        return repository.findAll();
    }

    public Optional<Sistemas> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Sistemas> buscarPorNome(String nome) {
        return repository.findByNome(nome);
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
