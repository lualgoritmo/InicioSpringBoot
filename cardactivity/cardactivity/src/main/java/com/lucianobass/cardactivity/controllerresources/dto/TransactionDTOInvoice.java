package com.lucianobass.cardactivity.controllerresources.dto;

public class TransactionDTOInvoice {
    private String description;
    private Float priceValue;
    private String transactionTime;

    public TransactionDTOInvoice(String description, Float priceValue, String transactionTime) {
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

    public Float getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Float priceValue) {
        this.priceValue = priceValue;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
}
