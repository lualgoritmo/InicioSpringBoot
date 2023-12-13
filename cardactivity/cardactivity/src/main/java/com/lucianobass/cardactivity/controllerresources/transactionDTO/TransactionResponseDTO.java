package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private CardHolderDTO cardHolder;
    private CardDTO card;
    private TransactionDTO transaction;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(CardHolderDTO cardHolder, CardDTO card, TransactionDTO transaction) {
        this.cardHolder = cardHolder;
        this.card = card;
        this.transaction = transaction;
    }

    public CardHolderDTO getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolderDTO cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }
}
