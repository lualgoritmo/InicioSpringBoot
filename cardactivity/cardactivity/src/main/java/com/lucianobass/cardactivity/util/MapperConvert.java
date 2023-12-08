package com.lucianobass.cardactivity.util;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.dto.TransactionDTO;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;

public class MapperConvert {

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

    public static CardHolder convertDTOToCardHolder(CardHolderDTO cardHolderDTO) {
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

    public static void validateCardHolder(CardHolder cardHolder) {
        if (cardHolder.getName().isEmpty() || cardHolder.getDocumentNumber().isEmpty() ||
                cardHolder.getBirthDate().isEmpty()) {
            throw new IllegalArgumentException(" O usuário não existe! ");
        }
    }

    public static Transaction convertDTOToTransacation(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            throw new IllegalArgumentException("DTO de transação não pode ser nulo");
        }
        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setPriceValue(transactionDTO.getPriceValue());
        transaction.setTransactionTime(transactionDTO.getTransactionTime());

        return transaction;
    }

    public static TransactionDTO convertTransacationToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO(
                transaction.getDescription(),
                transaction.getPriceValue(),
                transaction.getTransactionTime());
        //transactionDTO.setTransactionTime(transaction.getTransactionTime(LocalDateTime.now()));

        return transactionDTO;
    }

    public static CardDTO convertCardToDTO(Card card) {
        return new CardDTO(
                card.getId(),
                card.getNumberCard(),
                card.getCardExpiration(),
                card.getAvailableLimit(),
                card.getCardLimit(),
                card.getCardCVV(),
                card.isCardActive(),
                card.getCardHolder().getId(),
                null
        );
    }
}
