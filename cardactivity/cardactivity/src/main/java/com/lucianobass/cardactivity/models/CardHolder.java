package com.lucianobass.cardactivity.models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "tb_cardholder")
public class CardHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank(message = "Escreva o seu nome")
    private String name;

    //@NotBlank(message = "Digite o seu documento")
    private String documentNumber;

    //@NotBlank(message = "Data de nascimento")
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

    public String generateNumberCard(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    @PrePersist
    public void prePersist() {
        if (this.card == null) {
            this.card = new Card();
            this.card.setNumberCard(generateNumberCard(16));
            this.card.setCardExpiration("2023-12-01");
            this.card.setCardActive(true);
            this.card.setCardHolder(this);
        }
    }
}