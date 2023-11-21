package com.lucianobass.cardactivity.controllerresource;

import com.lucianobass.cardactivity.modelsentitys.Card;
import com.lucianobass.cardactivity.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cards")
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping
    public ResponseEntity<List<Card>> createCard() {
        List<Card> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

//    @GetMapping
//    public ResponseEntity<List<Card>> createCard() {
//        List<Card> list = new ArrayList<>();
//        list.add(new Card(1L, "João", "000000000000", "21/11/2023", "123"));
//        list.add(new Card(2L, "Maria", "111111111111", "21/11/2023", "456"));
//        list.add(new Card(3L, "Pedro", "222222222222", "21/11/2023", "789"));
//        return ResponseEntity.ok().body(list);
//    }
}
