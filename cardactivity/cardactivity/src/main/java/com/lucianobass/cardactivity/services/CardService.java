package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.modelsentitys.Card;
import com.lucianobass.cardactivity.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public List<Card> findAll() {
        List<Card> list = new ArrayList<>();
        return repository.findAll();
    }

    //FAZER O DTO DEPOIS
    public Card createCard(Card card) {
        Card car = new Card();
        card.setName(car.getName());
        card.setNumberCard(car.getNumberCard());
        card.setDateFinal(car.getDateFinal());
        card.setCodSegurance(car.getCodSegurance());
        car = repository.save(car);
        return car;
        //return new CardDTO(car)
    }
}
