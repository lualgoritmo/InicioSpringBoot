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

//    @Transactional()
//    public CardHolder createCard(CardHolderDTO cardHolderDTO) {
//        validateCardHolder(cardHolderDTO);
//        try {
//            return cardHolderRepository.save(setCardHolder(cardHolderDTO));
//        } catch (Exception ex) {
//            System.out.println("ERRO AO CRIAR");
//            throw ex;
//        }
//
//    }

    @Transactional
    public CardHolderDTO createCard(CardHolderDTO cardHolderDTO) {
        validateCardHolder(cardHolderDTO);
        try {
            CardHolder savedCardHolderDTO = cardHolderRepository.save(setCardHolder(cardHolderDTO));
            CardHolderDTO responseDTO = convertToResponseDTO(savedCardHolderDTO);
            return responseDTO;
        } catch (Exception ex) {
            System.out.println("ERRO AO CRIAR");
            throw ex;
        }
    }

//    @Transactional
//    public CardHolder createCard(CardHolderDTO cardHolderDTO) {
//        try {
//            validateCardHolder(cardHolderDTO);
//            CardHolder cardHolder = setCardHolder(cardHolderDTO);
//            CardHolder savedCardHolder = cardHolderRepository.save(cardHolder);
//            ;
//            System.out.println("createCard: TESTANDO UM  " + savedCardHolder.generateNumberCard(16));
//            System.out.println("CARDHOLDER RETORNADO: " + savedCardHolder);
//            return savedCardHolder;
//        } catch (Exception e) {
//            System.out.println("ERRO AO CRIAR");
//            throw e;
//        }
//    }

    @Transactional()
    public List<CardHolder> getAllCardsHolders() {
        return cardHolderRepository.findAll();
    }

    @Transactional()
    public CardHolder getByIdCardHolder(@PathVariable Long id) {
        return cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );
    }

    private CardHolderDTO convertToResponseDTO(CardHolder cardHolder) {
        CardHolderDTO responseDTO = new CardHolderDTO();
        responseDTO.setName(cardHolder.getName());
        responseDTO.setDocumentNumber(cardHolder.getDocumentNumber());
        responseDTO.setBirthDate(cardHolder.getBirthDate());
        return responseDTO;
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

    @Transactional()
    public void deleteIdCard(@PathVariable Long id) {
        try {
            cardHolderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(id);
        }
    }
}
