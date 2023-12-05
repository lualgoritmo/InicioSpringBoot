package com.lucianobass.cardactivity.util;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.models.CardHolder;

public class CardHolderValidator {

    public static CardHolderDTO convertToResponseDTO(CardHolder cardHolder) {
        CardHolderDTO responseDTO = new CardHolderDTO();
        responseDTO.setName(cardHolder.getName());
        responseDTO.setDocumentNumber(cardHolder.getDocumentNumber());
        responseDTO.setBirthDate(cardHolder.getBirthDate());

        if (cardHolder.getCard() != null) {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setNumberCard(cardHolder.getCard().getNumberCard());
            cardDTO.setCardExpiration(cardHolder.getCard().getCardExpiration());
            cardDTO.setCardLimit(cardHolder.getCard().getCardLimit());
            cardDTO.setCardCVV(cardHolder.getCard().getCardCVV());
            cardDTO.setAvailableLimit(cardHolder.getCard().getAvailableLimit());

            // Importante: Atualizar cardActive no DTO após a ativação
            cardDTO.setCardActive(cardHolder.getCard().getCardActive());

            responseDTO.setCard(cardDTO);
        }

        return responseDTO;
    }

    public static CardHolder setCardHolder(CardHolderDTO cardHolderDTO) {
        CardHolder cardHolder = new CardHolder();
        cardHolder.setName(cardHolderDTO.getName());
        cardHolder.setDocumentNumber(cardHolderDTO.getDocumentNumber());
        if (cardHolderDTO.getName() != null) {
            cardHolder.setBirthDate(cardHolderDTO.getBirthDate());
        } else {
            System.out.println("DATA CHEGANDO NULL");
        }
        return cardHolder;
    }

    public static void validateCardHolder(CardHolderDTO cardHolderDTO) {
        if (cardHolderDTO.getName().isEmpty() || cardHolderDTO.getDocumentNumber().isEmpty() ||
                cardHolderDTO.getBirthDate().isEmpty()) {
            throw new IllegalArgumentException(" O usuário não existe! ");
        }
    }
}
