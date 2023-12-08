package com.lucianobass.cardactivity.controllerresources.dto;

public class CardTransactionDTO {
    private String numberCard;
    private String expiration;
    private String cvv;

    public CardTransactionDTO() {
    }

    public CardTransactionDTO(String numberCard, String expiration, String cvv) {
        this.numberCard = numberCard;
        this.expiration = expiration;
        this.cvv = cvv;
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
