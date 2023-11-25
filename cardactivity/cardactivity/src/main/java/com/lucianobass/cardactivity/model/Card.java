package com.lucianobass.cardactivity.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "tb_card")
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "Forneça um nome de usuário")
    private String name;
    @Column(length = 16, nullable = false)
    @Size(min = 16, max = 16, message = "O Cartão deve ter 16 digitos")
    private String numberCard;
    @Size(min = 5, max = 5, message = "A data deve ser xx/xx")
    private String dateFinal;
    @Column(length = 3, nullable = false)
    private String codSegurance;

    public Card(Long id, String name, String numberCard, String dateFinal, String codSegurance) {
        this.id = id;
        this.name = name;
        this.numberCard = numberCard;
        this.dateFinal = dateFinal;
        this.codSegurance = codSegurance;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(String dateFinal) {
        this.dateFinal = dateFinal;
    }

    public String getCodSegurance() {
        return codSegurance;
    }

    public void setCodSegurance(String codSegurance) {
        this.codSegurance = codSegurance;
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
