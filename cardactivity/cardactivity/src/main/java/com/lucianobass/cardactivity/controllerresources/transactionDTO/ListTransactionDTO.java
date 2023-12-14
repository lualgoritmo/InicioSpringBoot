package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import java.util.List;

public class ListTransactionDTO {

   private CardHolderTransactionDTO cardHolder;
   private CardTransactionDTO card;
   private List<TransactionDTOTra> transaction;

    public ListTransactionDTO() {
    }

    public ListTransactionDTO(CardHolderTransactionDTO cardHolderDTO,CardTransactionDTO cardDTO,
                              List<TransactionDTOTra> transactionDTO) {
        this.cardHolder = cardHolderDTO;
        this.card = cardDTO;
        this.transaction = transactionDTO;
    }

    public CardHolderTransactionDTO getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolderTransactionDTO cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardTransactionDTO getCard() {
        return card;
    }

    public void setCard(CardTransactionDTO card) {
        this.card = card;
    }

    public List<TransactionDTOTra> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<TransactionDTOTra> transaction) {
        this.transaction = transaction;
    }
}
