package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import java.util.List;

public class ListTransactionDTO {

   private CardHolderTransactionDTO cardHolderDTO;
   private CardTransactionDTO cardDTO;
   private List<TransactionDTOInvoice> transactionDTO;

    public ListTransactionDTO() {
    }

    public ListTransactionDTO(CardHolderTransactionDTO cardHolderDTO,CardTransactionDTO cardDTO,
                              List<TransactionDTOInvoice> transactionDTO) {
        this.cardHolderDTO = cardHolderDTO;
        this.cardDTO = cardDTO;
        this.transactionDTO = transactionDTO;
    }

    public CardHolderTransactionDTO getCardHolderDTO() {
        return cardHolderDTO;
    }

    public void setCardHolderDTO(CardHolderTransactionDTO cardHolderDTO) {
        this.cardHolderDTO = cardHolderDTO;
    }

    public CardTransactionDTO getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(CardTransactionDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

    public List<TransactionDTOInvoice> getTransactionDTO() {
        return transactionDTO;
    }

    public void setTransactionDTO(List<TransactionDTOInvoice> transactionDTO) {
        this.transactionDTO = transactionDTO;
    }
}
