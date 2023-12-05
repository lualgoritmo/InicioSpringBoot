package com.lucianobass.cardactivity.controllerresources.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonIgnore
    private Long id;
    private String description;
    private String transactionTime;

    public TransactionDTO() {
    }

    public TransactionDTO(String description, String transactionTime) {
        this.description = description;
        this.transactionTime = transactionTime;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
