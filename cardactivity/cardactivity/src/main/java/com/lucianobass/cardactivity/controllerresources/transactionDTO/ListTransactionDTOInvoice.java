package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;

import java.util.List;

public class ListTransactionDTOInvoice {
    private CardHolderDTO cardHolder;
    private CardTransactionDTO card;
    private InvoiceDTO invoice;

    public ListTransactionDTOInvoice() {
    }

    public ListTransactionDTOInvoice(CardHolderDTO cardHolder, CardTransactionDTO card, InvoiceDTO invoice) {
        this.cardHolder = cardHolder;
        this.card = card;
        this.invoice = invoice;
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

    public void setCard(CardTransactionDTO card) {
        this.card = card;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }
}