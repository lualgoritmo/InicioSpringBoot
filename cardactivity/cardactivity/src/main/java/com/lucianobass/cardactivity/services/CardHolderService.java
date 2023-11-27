package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CardHolderService {

    @Autowired
    private CardHolderRepository cardHolderRepository;

    private CardHolder setCardHolder(CardHolderDTO cardHolderDTO) {
        CardHolder cardHolder = new CardHolder();
        cardHolder.setName(cardHolderDTO.getName());
        cardHolder.setDocumentNumber(cardHolderDTO.getDocumentNumber());
        cardHolder.setBirthDate(cardHolderDTO.getBirthDate());
        return cardHolder;
    }

    private void validateCardHolder(CardHolderDTO cardHolderDTO) {
        if (cardHolderDTO.getName().isEmpty()) {
            throw new IllegalArgumentException(" O usuário não existe! ");
        }
    }

    @Transactional()
    public CardHolder createCard(CardHolderDTO cardHolderDTO) {
        validateCardHolder(cardHolderDTO);
        return cardHolderRepository.save(setCardHolder(cardHolderDTO));
    }

    @Transactional()
    public List<CardHolder> getAllCardsHolders() {
        return cardHolderRepository.findAll();
    }

}