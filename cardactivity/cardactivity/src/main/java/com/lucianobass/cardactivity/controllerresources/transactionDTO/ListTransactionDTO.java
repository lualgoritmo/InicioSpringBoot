package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;

import java.util.List;

public class ListTransactionDTO {
    private CardHolderDTO cardHolder;
    private CardTransactionDTO card;
    private List<ListTransactionDTOInvoice> invoices;

    public ListTransactionDTO() {
    }

    public ListTransactionDTO(CardHolderDTO cardHolderDTO, CardTransactionDTO cardDTO, List<ListTransactionDTOInvoice> invoices) {
        this.cardHolder = cardHolderDTO;
        this.card = cardDTO;
        this.invoices = invoices;
    }

    public CardHolderDTO getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolderDTO cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardTransactionDTO getCard() {
        return card;
    }

    public void setCard(CardTransactionDTO cardTransactionDTO) {
        this.card = cardTransactionDTO;
    }

    public List<ListTransactionDTOInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<ListTransactionDTOInvoice> invoices) {
        this.invoices = invoices;
    }
}
