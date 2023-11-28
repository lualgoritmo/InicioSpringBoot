package com.lucianobass.cardactivity.controllerresources.dto;

import com.lucianobass.cardactivity.models.CardHolder;

import java.io.Serializable;

public class CardHolderDTO implements Serializable {
    private static final long serialVersionUIO = 1L;

    private Long id;
    private String name;
    private String documentNumber;
    private String birthDate;

    public CardHolderDTO(Long id, String name, String documentNumber, String birthDate) {
        this.id = id;
        this.name = name;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
    }

    public CardHolderDTO() {

    }

    public CardHolderDTO(CardHolder entity) {
        this.name = entity.getName();
        this.documentNumber = entity.getDocumentNumber();
        this.birthDate = entity.getBirthDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

}
