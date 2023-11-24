package com.lucianobass.cardactivity;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.ranges.Range;

import java.util.Random;

@SpringBootApplication
public class CardactivityApplication {

    public static void main(String[] args) {

        SpringApplication.run(CardactivityApplication.class, args);
        System.out.println("Hello Word! Funcionando");

//		CardDTO cardDTO = new CardDTO();
//		cardDTO.setNumberCard("123456789012345");
//
//		String formattedCardNumber = cardDTO.getFormattedNumberCard();
//		System.out.println("Número formatado: " + formattedCardNumber);
    }
}
