package com.lucianobass.cardactivity.service;

import com.lucianobass.cardactivity.exception.CardNotFoundException;
import com.lucianobass.cardactivity.model.Card;
import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.repositorie.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public List<Card> findAll() {
        //List<Card> list = new ArrayList<>();
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Card findByIdCard(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new CardNotFoundException(id)
        );
    }
//    @Transactional(readOnly = true)
//    public Optional<Card> findByIdCard(Long id) {
//        return repository.findById(id);
//    }

    @Transactional(readOnly = true)
    public Card createCard(CardDTO cardDTO) {
        validateCardDTO(cardDTO);
        return repository.save(setCardDTO(cardDTO));
    }

    //Uma validação, no meu entender, para ver se o cartão existe ou não, afinal o dado da segundo
    //Requisitado no bd
    private void validateCardDTO(CardDTO cardDTO) {
        if (cardDTO.getName().isEmpty()) {
            throw new IllegalArgumentException(" Cartão não existe! ");
        }
    }

    private Card setCardDTO(CardDTO cardDTO) {
        Card cardCredit = new Card();
        cardCredit.setName(cardDTO.getName());
        cardCredit.setNumberCard(cardDTO.getNumberCard());
        cardCredit.setDateFinal(cardDTO.getDateFinal());
        cardCredit.setCodSegurance(cardDTO.getCodSegurance());
        return cardCredit;
    }
    //FAZER O DTO DEPOIS
//    public Card createCard(Card card) {
    //Card car = new Card();
//        card.setName(car.getName());
//        card.setNumberCard(car.getNumberCard());
//        card.setDateFinal(car.getDateFinal());
//        card.setCodSegurance(car.getCodSegurance());
//        car = repository.save(car);
//        return repository.save(card);
//        //return new CardDTO(car)
//    }
}
