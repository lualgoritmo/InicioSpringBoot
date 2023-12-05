package com.lucianobass.cardactivity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_transation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTransacation;
    private String description;
    private Float priceValue;
    private String transactionTime;
    @OneToOne
    @JoinColumn(name = "card_holder_id")
    @JsonIgnoreProperties("transaction")
    @JsonIgnore
    private CardHolder cardHolder;

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

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
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
