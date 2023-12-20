package com.lucianobass.cardactivity.controllerresources.invoiceDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDTO implements Serializable {

    private Float total;
    private String status;
    private List<TransactionDTO> transactions;

    public InvoiceDTO() {
    }

    public InvoiceDTO(Float total, String status, List<TransactionDTO> transactions) {
        this.total = total;
        this.status = status;
        this.transactions = transactions;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
