package com.lucianobass.cardactivity.controllerresources.dto;

import com.lucianobass.cardactivity.models.Card;

import java.io.Serializable;

public class CardDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String numberCard;
    private String cardExpiration;
    private String availableLimit;
    private String cardLimit;
    private String cardCVV;
    private boolean cardActive;
    private Long cardHolderId;

    public CardDTO() {
    }

    public CardDTO(Long id, String numberCard, String cardExpiration, String availableLimit, String cardLimit, String cardCVV, boolean cardActive, Long cardHolderId) {
        this.id = id;
        this.numberCard = numberCard;
        this.cardExpiration = cardExpiration;
        this.availableLimit = availableLimit;
        this.cardLimit = cardLimit;
        this.cardCVV = cardCVV;
        this.cardActive = cardActive;
        this.cardHolderId = cardHolderId;
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
        this.numberCard =  Card.generateNumberCard(16).replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x");
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

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = Card.generateNumberCard(3).replaceAll("(\\d)", "x");;
    }

    public boolean getCardActive() {
        return cardActive;
    }

    public void setCardActive(boolean cardActive) {
        this.cardActive = cardActive;
    }

    public Long getCardHolderId() {
        return cardHolderId;
    }
}

