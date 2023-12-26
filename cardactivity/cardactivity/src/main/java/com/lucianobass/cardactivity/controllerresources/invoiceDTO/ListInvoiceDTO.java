package com.lucianobass.cardactivity.controllerresources.invoiceDTO;

import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardHolderTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;

import java.util.List;

public class ListInvoiceDTO {
    private CardHolderTransactionDTO cardHolder;
    private CardTransactionDTO card;
    private List<InvoiceDTO> invoices;

    public ListInvoiceDTO() {
    }

    public ListInvoiceDTO(CardHolderTransactionDTO cardHolderDTO, CardTransactionDTO cardDTO, List<InvoiceDTO> invoices) {
        this.cardHolder = cardHolderDTO;
        this.card = cardDTO;
        this.invoices = invoices;
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

    public List<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }

}
