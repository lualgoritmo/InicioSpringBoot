package com.lucianobass.cardactivity.controllerresources.invoiceDTO;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardHolderTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;

import java.util.List;

public class ListInvoiceDTO {
    private CardHolderTransactionDTO cardHolderDTO;
    private CardTransactionDTO cardDTO;
    private List<InvoiceDTO> invoiceDTOS;
    private CardHolderTransactionDTO cardHolder;

    public ListInvoiceDTO() {
    }

    public ListInvoiceDTO(CardHolderTransactionDTO cardHolderDTO, CardTransactionDTO cardDTO, List<InvoiceDTO> invoices, CardHolderTransactionDTO cardHolder) {
        this.cardHolderDTO = cardHolderDTO;
        this.cardDTO = cardDTO;
        this.invoiceDTOS = invoices;
        this.cardHolder = cardHolder;
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

    public List<InvoiceDTO> getInvoiceDTOS() {
        return invoiceDTOS;
    }

    public void setInvoiceDTOS(List<InvoiceDTO> invoiceDTOS) {
        this.invoiceDTOS = invoiceDTOS;
    }

    public CardHolderTransactionDTO getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolderTransactionDTO cardHolder) {
        this.cardHolder = cardHolder;
    }
}
