package com.lucianobass.cardactivity.controllerresources.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonIgnore
    private Long idTransacation;
    private String description;
    private String transactionTime;
    private Long cardHolderId;

    public TransactionDTO() {
    }

    public TransactionDTO(Long idTransaction, String description, String transactionTime, Long cardHolderId) {
        this.idTransacation = idTransaction;
        this.description = description;
        this.transactionTime = transactionTime;
        this.cardHolderId = cardHolderId;
    }

    public Long getIdTransacation() {
        return idTransacation;
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
