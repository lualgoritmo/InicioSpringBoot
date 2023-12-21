package com.lucianobass.cardactivity.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    private String description;
    private Float priceValue;
    private LocalDateTime transactionTime;
    @ManyToOne
    //@JsonIgnoreProperties("transactions")
    @JoinColumn(name = "card_id")
    @JsonBackReference
    private Card card;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private Invoice invoice;

    public Transaction() {
    }

    public Transaction(String description, Float priceValue) {
        this.description = description;
        this.priceValue = priceValue;
        this.transactionTime = LocalDateTime.now();

    }

    public Long getIdTransaction() {
        return idTransaction;
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return idTransaction.equals(that.idTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaction);
    }
}
