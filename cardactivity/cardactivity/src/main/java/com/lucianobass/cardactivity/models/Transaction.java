package com.lucianobass.cardactivity.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTransacation;
    private String description;
    private Float priceValue;
    private String transactionTime;

    public Transaction() {

    }

    public Transaction(String description, Float priceValue, String transactionTime) {
        this.description = description;
        this.priceValue = priceValue;
        this.transactionTime = transactionTime;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return idTransacation.equals(that.idTransacation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransacation);
    }
}
