package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CardHolderService {

    @Autowired
    private CardHolderRepository cardHolderRepository;

    @Transactional()
    public CardHolder createCard(CardHolderDTO cardHolderDTO) {
        validateCardHolder(cardHolderDTO);
        try {
            return cardHolderRepository.save(setCardHolder(cardHolderDTO));
        } catch (Exception ex) {
            System.out.println("ERRO AO CRIAR");
            throw ex;
        }

    }

    @Transactional()
    public List<CardHolder> getAllCardsHolders() {
        return cardHolderRepository.findAll();
    }

    @Transactional
    public CardHolder getByIdCardHolder(@PathVariable Long id) {
        return cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );
    }

    public void deleteId(Long id) {
        try {
            cardHolderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(id);
        }
    }

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
}
