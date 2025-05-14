package semtd_intranet.semtd_net.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import semtd_intranet.semtd_net.model.Cardshomepage;
import semtd_intranet.semtd_net.repository.CardshomepageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/basedigital")
public class CardshomepageController {

    @Autowired
    private CardshomepageRepository cardshomepagerepository;

    @GetMapping("/cardshomepage")
    public ResponseEntity<List<Cardshomepage>> getAllCards() {
        List<Cardshomepage> cards = cardshomepagerepository.findAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/cardshomepage/{id}")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @PostMapping("/cardshomepage/cadastrarcard")
    public ResponseEntity<Cardshomepage> createCard(@Valid @RequestBody Cardshomepage novoCard) {
        Cardshomepage salvo = cardshomepagerepository.save(novoCard);
        return ResponseEntity.status(201).body(salvo); // 201 CREATED
    }
}

/*
 * Boilerplate
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
 */
