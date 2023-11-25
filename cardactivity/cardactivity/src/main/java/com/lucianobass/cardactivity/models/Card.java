package com.lucianobass.cardactivity.models;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_card")
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16, nullable = false)
    private String numberCard;
    private String expiration;
    private String availableLimit;
    private String card_limit;
    @Column(length = 3, nullable = false)
    private String cvv;
    private boolean active = false;
    @OneToOne()
    @JoinColumn(name = "card_holder_id")
    private CardHolder cardHolder;

    public Card(Long id, String numberCard, String expiration, String availableLimit, String card_limit, String cvv, boolean active, CardHolder cardHolder) {
        this.id = id;
        this.numberCard = numberCard;
        this.expiration = expiration;
        this.availableLimit = availableLimit;
        this.card_limit = card_limit;
        this.cvv = cvv;
        this.active = active;
        this.cardHolder = cardHolder;

    }

    public Card() {
    }

    public String getCard_limit() {
        return card_limit;
    }

    public void setCard_limit(String card_limit) {
        this.card_limit = card_limit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(String availableLimit) {
        this.availableLimit = availableLimit;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }
}
