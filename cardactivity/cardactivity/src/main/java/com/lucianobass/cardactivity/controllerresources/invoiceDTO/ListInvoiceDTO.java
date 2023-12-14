package com.lucianobass.cardactivity.controllerresources.invoiceDTO;

import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardHolderTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;

import java.util.List;

public class ListInvoiceDTO {
    private CardHolderTransactionDTO cardHolder;
    private CardTransactionDTO card;
    private List<InvoiceDTO> invoiceDTOList;

    public ListInvoiceDTO() {
    }

    public ListInvoiceDTO(
            CardHolderTransactionDTO cardHolder,
            CardTransactionDTO card,
            List<InvoiceDTO> invoiceDTOList) {
        this.cardHolder = cardHolder;
        this.card = card;
        this.invoiceDTOList = invoiceDTOList;
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

    public List<InvoiceDTO> getInvoiceDTOList() {
        return invoiceDTOList;
    }

    public void setInvoiceDTOList(List<InvoiceDTO> invoiceDTOList) {
        this.invoiceDTOList = invoiceDTOList;
    }
}
