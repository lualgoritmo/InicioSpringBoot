package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;

import java.io.Serializable;

public class TransactionResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private CardHolderDTO cardHolderDTO;
    private CardDTO cardDTO;
    private TransactionDTO transactionDTO;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(CardHolderDTO cardHolder, CardDTO card, TransactionDTO transaction) {
        this.cardHolderDTO = cardHolder;
        this.cardDTO = card;
        this.transactionDTO = transaction;
    }

    public CardHolderDTO getCardHolderDTO() {
        return cardHolderDTO;
    }

    public void setCardHolderDTO(CardHolderDTO cardHolderDTO) {
        this.cardHolderDTO = cardHolderDTO;
    }

    public CardDTO getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

    public TransactionDTO getTransactionDTO() {
        return transactionDTO;
    }

    public void setTransactionDTO(TransactionDTO transactionDTO) {
        this.transactionDTO = transactionDTO;
    }
}
