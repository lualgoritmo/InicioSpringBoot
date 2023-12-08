package com.lucianobass.cardactivity.controllerresources.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idTransacation;
    private String description;
    private String transactionTime;
    private Float priceValue;
    private Long cardHolderId;

    public TransactionDTO() {
    }

    public TransactionDTO(String description,Float priceValue, String transactionTime) {
        this.description = description;
        this.priceValue = priceValue;
        this.transactionTime = transactionTime;
    }

    public Long getIdTransacation() {
        return idTransacation;
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

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
}
