package semtd_intranet.semtd_net.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import semtd_intranet.semtd_net.model.Cardshomepage;
import semtd_intranet.semtd_net.repository.CardshomepageRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/basedigital")
public class CardshomepageController {

    @Autowired
    private CardshomepageRepository cardshomepagerepository;

    // GET todos os cards
    @GetMapping("/cardshomepage")
    public ResponseEntity<List<Cardshomepage>> getAllCards() {
        List<Cardshomepage> cards = cardshomepagerepository.findAll();
        return ResponseEntity.ok(cards);
    }

    // GET card por título (via RequestParam)
    @GetMapping("/cardshomepage/buscartitulo")
    public ResponseEntity<List<Cardshomepage>> getByTitulo(@RequestParam String titulo) {
        List<Cardshomepage> cards = cardshomepagerepository.findByTituloContainingIgnoreCase(titulo);
        if (cards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/cardshomepage/cadastrarcard")
    public ResponseEntity<Cardshomepage> createCard(@Valid @RequestBody Cardshomepage novoCard) {
        Cardshomepage salvo = cardshomepagerepository.save(novoCard);
        return ResponseEntity.status(201).body(salvo);
    }

    // PUT para atualizar um card existente
    @PutMapping("/cardshomepage/{id}")
    public ResponseEntity<Cardshomepage> updateCard(@PathVariable Long id,
            @Valid @RequestBody Cardshomepage cardAtualizado) {
        Optional<Cardshomepage> optionalCard = cardshomepagerepository.findById(id);

        if (optionalCard.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cardshomepage existente = optionalCard.get();
        existente.setTitulo(cardAtualizado.getTitulo());
        existente.setDescricao(cardAtualizado.getDescricao());
        existente.setLink(cardAtualizado.getLink());

        Cardshomepage atualizado = cardshomepagerepository.save(existente);
        return ResponseEntity.ok(atualizado);
    }

    // PUT para atualizar card por título (assume o primeiro resultado encontrado,
    // espera-se que o título seja único.)
    @PutMapping("/cardshomepage")
    public ResponseEntity<Cardshomepage> updateCardByTitulo(@RequestParam String titulo,
            @Valid @RequestBody Cardshomepage cardAtualizado) {
        List<Cardshomepage> cards = cardshomepagerepository.findByTituloContainingIgnoreCase(titulo);
        if (cards.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cardshomepage existente = cards.get(0); // Atualiza o primeiro encontrado
        existente.setTitulo(cardAtualizado.getTitulo());
        existente.setDescricao(cardAtualizado.getDescricao());
        existente.setLink(cardAtualizado.getLink());

        Cardshomepage atualizado = cardshomepagerepository.save(existente);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE por ID
    @DeleteMapping("/cardshomepage/{id}")
    public ResponseEntity<Void> deleteCardById(@PathVariable Long id) {
        if (!cardshomepagerepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cardshomepagerepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE por título (deleta todos os cards com título correspondente)
    @DeleteMapping("/cardshomepage")
    public ResponseEntity<Void> deleteCardByTitulo(@RequestParam String titulo) {
        List<Cardshomepage> cards = cardshomepagerepository.findByTituloContainingIgnoreCase(titulo);
        if (cards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cardshomepagerepository.deleteAll(cards);
        return ResponseEntity.noContent().build();
    }

}

/*
 * template
 * 
 * @PostMapping(value = "/addFilm", consumes = {"application/json",
 * "application/xml"}, produces = {"application/json", "application/xml"})
 * public ResponseEntity<Film> addFilm(@RequestBody Film film) {
 * if (film.getFilmID() != 0 && filmRepository.existsById(film.getFilmID())) {
 * return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
 * }
 * Film savedFilm = filmRepository.save(film);
 * return ResponseEntity.status(HttpStatus.CREATED).body(savedFilm);
 * }
 * 
 * 
 * 
 * 
 * 
 * @PutMapping(value = "/modify/{id}", consumes = {"application/json",
 * "application/xml"}, produces = {"application/json", "application/xml"})
 * public ResponseEntity<Film> modifyFilm(@PathVariable int id, @RequestBody
 * Film film) {
 * if (!filmRepository.existsById(id)) {
 * return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
 * }
 * film.setFilmID(id);
 * Film updatedFilm = filmRepository.save(film);
 * return ResponseEntity.ok(updatedFilm);
 * }
 */
