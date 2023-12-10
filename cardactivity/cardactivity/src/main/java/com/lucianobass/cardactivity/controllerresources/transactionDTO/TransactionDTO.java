package com.lucianobass.cardactivity.controllerresources.transactionDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idTransacation;
    private String description;
    private Double priceValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionTime;
    private Long cardHolderId;

    public TransactionDTO() {
    }

    public TransactionDTO(String description, Double priceValue, LocalDateTime transactionTime) {
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

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {

        this.transactionTime = transactionTime;
    }
}
