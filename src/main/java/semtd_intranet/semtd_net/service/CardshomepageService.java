package semtd_intranet.semtd_net.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import semtd_intranet.semtd_net.model.Cardshomepage;
import semtd_intranet.semtd_net.repository.CardshomepageRepository;

@Service
public class CardshomepageService {

    @Autowired
    private CardshomepageRepository cardshomepageRepository;

    public List<Cardshomepage> findAll() {
        return cardshomepageRepository.findAll();
    }

    public Optional<Cardshomepage> findById(Long id) {
        return cardshomepageRepository.findById(id);
    }

    // Título não pode ser igual a outro card já existente
    public Cardshomepage save(Cardshomepage card) {
        boolean tituloJaExiste = cardshomepageRepository
                .findByTituloContainingIgnoreCase(card.getTitulo())
                .stream()
                .anyMatch(existingCard -> !existingCard.getId().equals(card.getId()));

        if (tituloJaExiste) {
            throw new IllegalArgumentException("Já existe um card com este título.");
        }

        return cardshomepageRepository.save(card);
    }

    public List<Cardshomepage> findByTituloContainingIgnoreCase(String titulo) {
        return cardshomepageRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Transactional
    public void deleteById(Long id) {
        cardshomepageRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return cardshomepageRepository.existsById(id);
    }

    @Transactional
    public void deleteAllByTituloContainingIgnoreCase(String titulo) {
        List<Cardshomepage> cards = cardshomepageRepository.findByTituloContainingIgnoreCase(titulo);
        cardshomepageRepository.deleteAll(cards);
    }
}
