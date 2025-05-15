package semtd_intranet.semtd_net.controllers;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import semtd_intranet.semtd_net.model.Cardshomepage;
import semtd_intranet.semtd_net.service.CardshomepageService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/basedigital")
public class CardshomepageController {

    @Autowired
    private CardshomepageService cardshomepageService;

    // GET PARA RETORNAR TODOS OS CARDS
    @GetMapping("/cardshomepage")
    public ResponseEntity<List<Cardshomepage>> getAllCards() {
        List<Cardshomepage> cards = cardshomepageService.findAll();
        return ResponseEntity.ok(cards);
    }

    // GET PARA RETORNAR UM CARD ESPECÍFICO POR TÍTULO.
    // ESPERA-SE QUE O TÍTULO SEJA ÚNICO
    @GetMapping("/cardshomepage/buscartitulo")
    public ResponseEntity<List<Cardshomepage>> getByTitulo(@RequestParam String titulo) {
        List<Cardshomepage> cards = cardshomepageService.findByTituloContainingIgnoreCase(titulo);
        if (cards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cards);
    }

    // POST PARA CADASTRO DE NOVO CARD
    @PostMapping("/cardshomepage/cadastrarcard")
    public ResponseEntity<Cardshomepage> createCard(@Valid @RequestBody Cardshomepage novoCard) {
        Cardshomepage salvo = cardshomepageService.save(novoCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // PUT PARA ATUALIZAR UM CARD UTILIZANDO ID COMO PARÂMETRO COMO PATHVARIABLE
    @PutMapping("/cardshomepage/{id}")
    public ResponseEntity<Cardshomepage> updateCard(@PathVariable Long id,
            @Valid @RequestBody Cardshomepage cardAtualizado) {
        Optional<Cardshomepage> optionalCard = cardshomepageService.findById(id);

        if (optionalCard.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cardshomepage existente = optionalCard.get();
        existente.setTitulo(cardAtualizado.getTitulo());
        existente.setDescricao(cardAtualizado.getDescricao());
        existente.setLink(cardAtualizado.getLink());

        Cardshomepage atualizado = cardshomepageService.save(existente);
        return ResponseEntity.ok(atualizado);
    }

    // PUT PARA ATUALIZAR UM CARD UTILIZANDO TÍTULO COMO PARÂMETRO
    @PutMapping("/cardshomepage")
    public ResponseEntity<Cardshomepage> updateCardByTitulo(@RequestParam String titulo,
            @Valid @RequestBody Cardshomepage cardAtualizado) {
        List<Cardshomepage> cards = cardshomepageService.findByTituloContainingIgnoreCase(titulo);
        if (cards.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cardshomepage existente = cards.get(0);
        existente.setTitulo(cardAtualizado.getTitulo());
        existente.setDescricao(cardAtualizado.getDescricao());
        existente.setLink(cardAtualizado.getLink());

        Cardshomepage atualizado = cardshomepageService.save(existente);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE CARD UTILIZANDO ID COMO PARÂMETRO
    @DeleteMapping("/cardshomepage/{id}")
    public ResponseEntity<Void> deleteCardById(@PathVariable Long id) {
        if (!cardshomepageService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cardshomepageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE CARD UTILIZANDO TÍTULO COMO PARÂMETRO
    @DeleteMapping("/cardshomepage")
    public ResponseEntity<Void> deleteCardByTitulo(@RequestParam String titulo) {
        List<Cardshomepage> cards = cardshomepageService.findByTituloContainingIgnoreCase(titulo);
        if (cards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cardshomepageService.deleteAllByTituloContainingIgnoreCase(titulo);
        return ResponseEntity.noContent().build();
    }
}
