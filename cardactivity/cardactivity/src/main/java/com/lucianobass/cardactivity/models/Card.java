package com.lucianobass.cardactivity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "tb_card")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16, nullable = false)
    private String numberCard;
    @Size(min = 5, max = 5, message = " A data deve ser xx/xx")
    private String cardExpiration;
    private String availableLimit;
    private String cardLimit;
    @Column(name = "cardcvv", length = 3, nullable = false)
    private String cardCVV;
    private boolean cardActive = false;
    @OneToOne()
    @JoinColumn(name = "card_holder_id")
    @JsonIgnoreProperties("card")
    @JsonIgnore
    private CardHolder cardHolder;

    public Card(Long id, String numberCard, String expiration, String availableLimit, String card_limit, String cvv, boolean active, CardHolder cardHolder) {
        this.id = id;
        this.numberCard = numberCard;
        this.cardExpiration = expiration;
        this.availableLimit = availableLimit;
        this.cardLimit = card_limit;
        this.cardCVV = cvv;
        this.cardActive = active;
        this.cardHolder = cardHolder;

    }

    public Card() {
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
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
        this.numberCard =Card.generateNumberCard(16).replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x");
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

    public void setCardCVV(String cardCVV) {
        this.cardCVV =  Card.generateNumberCard(3).replaceAll("(\\d)", "x");
    }

    public boolean isCardActive() {
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

    public static String generateNumberCard(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
