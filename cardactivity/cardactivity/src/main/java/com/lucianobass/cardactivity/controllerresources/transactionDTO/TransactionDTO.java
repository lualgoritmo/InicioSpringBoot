package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO implements Serializable {

    private String description;
    private Float priceValue;
    private LocalDateTime transactionTime;

    public TransactionDTO() {
        this.transactionTime = LocalDateTime.now();
    }

    public TransactionDTO(String description, Float priceValue) {
        this.description = description;
        this.priceValue = priceValue;
        this.transactionTime = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public Float getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Float priceValue) {
        this.priceValue = priceValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = LocalDateTime.now();
    }
}
