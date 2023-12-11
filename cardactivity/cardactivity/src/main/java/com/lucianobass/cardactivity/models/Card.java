package com.lucianobass.cardactivity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_card")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long idCard;
    @Column(length = 16, nullable = false)
    private String numberCard;
    @Size(min = 5, max = 5, message = " A data deve ser xx/xx")
    private String cardExpiration;
    private String availableLimit;
    private Double cardLimit;
    @Column(name = "cardcvv", length = 3, nullable = false)
    private String cardCVV;
    private Boolean cardActive = false;
    @OneToOne()
    @JoinColumn(name = "card_holder_id")
    private CardHolder cardHolder;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Card(Long idCard, String numberCard, String expiration, String availableLimit,
                Double card_limit, String cvv, boolean active,
                CardHolder cardHolder, List<Transaction> transactions) {
        this.idCard = idCard;
        this.numberCard = numberCard;
        this.cardExpiration = expiration;
        this.availableLimit = availableLimit;
        this.cardLimit = card_limit;
        this.cardCVV = cvv;
        this.cardActive = active;
        this.cardHolder = cardHolder;
        this.transactions = transactions;
    }

    public Card() {
    }

    public Double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(Double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public String getNumberCard() {
        return numberCard;
    }

    //    public void setNumberCard(String numberCard) {
//        this.numberCard = Card.generateNumberCard(16).replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x");
//    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(String availableLimit) {
        this.availableLimit = availableLimit;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    //    public void setCardCVV(String cardCVV) {
//        this.cardCVV = Card.generateNumberCard(3).replaceAll("(\\d)", "x");
//    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public boolean getCardActive() {
        return cardActive;
    }

    public void setCardActive(boolean cardActive) {
        this.cardActive = cardActive;
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Boolean getCardActivate() {
        return cardActive;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCard);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(idCard, card.idCard);
    }
}
