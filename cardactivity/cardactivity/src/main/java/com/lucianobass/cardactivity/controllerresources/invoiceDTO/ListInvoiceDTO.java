package com.lucianobass.cardactivity.controllerresources.invoiceDTO;

import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardHolderTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;

import java.util.List;

public class ListInvoiceDTO {
    private CardHolderTransactionDTO cardHolderDTO;
    private CardTransactionDTO cardDTO;
    private List<InvoiceDTO> listInvoiceDTO;

    public ListInvoiceDTO() {
    }

    public ListInvoiceDTO(
            CardHolderTransactionDTO cardHolderDTO,
            CardTransactionDTO cardTransactionDTO,
            List<InvoiceDTO> listInvoiceDTO) {
        this.cardHolderDTO = cardHolderDTO;
        this.cardDTO = cardTransactionDTO;
        this.listInvoiceDTO = listInvoiceDTO;
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

    public void setCardDTO(CardTransactionDTO cardTransactionDTO) {
        this.cardDTO = cardTransactionDTO;
    }

    public List<InvoiceDTO> getListInvoiceDTO() {
        return listInvoiceDTO;
    }

    public void setListInvoiceDTO(List<InvoiceDTO> listInvoiceDTO) {
        this.listInvoiceDTO = listInvoiceDTO;
    }
}
