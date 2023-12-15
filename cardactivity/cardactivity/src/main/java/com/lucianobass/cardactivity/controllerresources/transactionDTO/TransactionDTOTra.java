package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import java.time.LocalDateTime;

public class TransactionDTOTra {
    private String description;
    private Float priceValue;
    private LocalDateTime transactionTime;

    public TransactionDTOTra(String description, Float priceValue) {
        this.description = description;
        this.priceValue = priceValue;
        this.transactionTime = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Float priceValue) {
        this.priceValue = priceValue;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = LocalDateTime.now();
    }
}
