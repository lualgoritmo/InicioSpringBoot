package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import java.time.LocalDateTime;

public class TransactionDTOInvoice {
    private String description;
    private Double priceValue;
    private LocalDateTime transactionTime;

    public TransactionDTOInvoice(String description, Double priceValue, LocalDateTime transactionTime) {
        this.description = description;
        this.priceValue = priceValue;
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
