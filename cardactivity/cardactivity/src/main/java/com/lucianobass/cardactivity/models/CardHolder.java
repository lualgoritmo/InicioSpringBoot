package com.lucianobass.cardactivity.models;

import javax.persistence.*;

@Entity
@Table(name = "tb_cardholder")
public class CardHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String documentNumber;
    private String birthDate;
    @OneToOne(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    private Card card;

    public CardHolder(String name, String documentNumber, String birthDate) {
        this.name = name;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
    }

    public CardHolder() {
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

    @PrePersist
    public void prePersist() {
        if (this.card == null) {
            this.card = new Card();
            this.card.setNumberCard(generateCardNumber());  // Lógica para gerar o número do cartão
            this.card.setExpiration("2023-12");  // Lógica para definir a data de vencimento
            this.card.setActive(true);  // Definindo o cartão como ativo
            this.card.setCardHolder(this);
        }
    }
}