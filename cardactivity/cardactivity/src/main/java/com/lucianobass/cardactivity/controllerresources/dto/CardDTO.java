package com.lucianobass.cardactivity.controllerresources.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lucianobass.cardactivity.models.Transaction;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO implements Serializable {

    private Long idCardDTO;
    private String numberCard;
    private String cardExpiration;
    private String availableLimit;
    private Double cardLimit;
    private String cardCVV;
    private Boolean cardActive;
    @JsonIgnore
    private Long cardHolderId;
    private List<Transaction> transactions;

    public CardDTO() {
    }

    public CardDTO(Long idCardDTO, String numberCard, String cardExpiration,
                   String availableLimit, Double cardLimit,
                   String cardCVV, boolean cardActive,
                   Long cardHolderId, List<Transaction> transactions) {
        this.idCardDTO = idCardDTO;
        this.numberCard = numberCard;
        this.cardExpiration = cardExpiration;
        this.availableLimit = availableLimit;
        this.cardLimit = cardLimit;
        this.cardCVV = cardCVV;
        this.cardActive = cardActive;
        this.cardHolderId = cardHolderId;
        this.transactions = transactions;
    }

    public Long getIdCardDTO() {
        return idCardDTO;
    }

    public void setIdCardDTO(Long idCardDTO) {
        this.idCardDTO = idCardDTO;
    }

    public String getNumberCard() {
        return numberCard;
    }

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

    public Double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(Double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
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

    public void setCardHolderId(Long cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    //    public Long getCardHolderId() {
//        return cardHolderId;
//    }
}

