package com.lucianobass.cardactivity.controllerresources.dto;

public class CardHolderTransactionDTO {
    private String name;
    private String documentNumber;
    private String birthDate;

    public CardHolderTransactionDTO() {
    }

    public CardHolderTransactionDTO(String name, String documentNumber, String birthDate) {
        this.name = name;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
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
