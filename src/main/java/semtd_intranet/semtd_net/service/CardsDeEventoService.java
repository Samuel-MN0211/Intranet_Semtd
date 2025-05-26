package semtd_intranet.semtd_net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import semtd_intranet.semtd_net.model.CardsDeEvento;
import semtd_intranet.semtd_net.repository.CardsDeEventoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardsDeEventoService {

    @Autowired
    private CardsDeEventoRepository repository;

    public CardsDeEvento salvar(CardsDeEvento card) {
        if (repository.existsByTitulo(card.getTitulo())) {
            throw new IllegalArgumentException("Já existe um card com este título.");
        }
        return repository.save(card);
    }

    public List<CardsDeEvento> listarTodos() {
        return repository.findAll();
    }

    public Optional<CardsDeEvento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<CardsDeEvento> buscarPorTitulo(String titulo) {
        return repository.findByTitulo(titulo);
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
