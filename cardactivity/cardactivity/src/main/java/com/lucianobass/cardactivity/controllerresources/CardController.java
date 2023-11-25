package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.model.Card;
import com.lucianobass.cardactivity.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cards")
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<Card> findAllCard() {
        return service.findAll();
    }
//    @GetMapping
//    public ResponseEntity<List<Card>> findAllCard() {
//        List<Card> list = service.findAll();
//        return ResponseEntity.ok().body(list);
//    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Card findCardById(@PathVariable Long id) {
        return service.findByIdCard(id);
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Card> findCardById(@PathVariable Long id) {
//        Optional<Card> card = service.findByIdCard(id);
//        if (card.isPresent()) {
//            return ResponseEntity.ok(card.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Card createCard(@RequestBody CardDTO cardDTO) {
        return service.createCard(cardDTO);
    }

//    @PostMapping
//    public ResponseEntity<Card> createCard(@RequestBody CardDTO cardDTO) {
//        Card createdCard = service.createCard(cardDTO);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(createdCard.getId()).toUri();
//        return ResponseEntity.created(uri).body(createdCard);
//    }
//
//    @PostMapping
//    //FAZER O DTO DEPIOS
//    public ResponseEntity<Card> createCard(@RequestBody Card card) {
//        card = service.createCard(card);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(card.getId()).toUri();
//        return ResponseEntity.created(uri).body(card);
//    }

//    @GetMapping
//    public ResponseEntity<List<Card>> createCard() {
//        List<Card> list = new ArrayList<>();
//        list.add(new Card(1L, "João", "000000000000", "21/11/2023", "123"));
//        list.add(new Card(2L, "Maria", "111111111111", "21/11/2023", "456"));
//        list.add(new Card(3L, "Pedro", "222222222222", "21/11/2023", "789"));
//        return ResponseEntity.ok().body(list);
//    }
}