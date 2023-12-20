package com.lucianobass.cardactivity.controllerresources.invoiceDTO;

import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardHolderTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;

import java.util.List;

public class ListInvoiceDTO {
    private CardHolderTransactionDTO cardHolderDTO;
    private CardTransactionDTO cardDTO;
    private List<InvoiceDTO> invoices;

    public ListInvoiceDTO() {
    }

    public ListInvoiceDTO(CardHolderTransactionDTO cardHolderDTO, CardTransactionDTO cardDTO, List<InvoiceDTO> invoices) {
        this.cardHolderDTO = cardHolderDTO;
        this.cardDTO = cardDTO;
        this.invoices = invoices;
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

    public List<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }
}
