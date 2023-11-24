package com.lucianobass.cardactivity;

import com.lucianobass.cardactivity.controllerresources.dtos.CardDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardactivityApplication {

	public static void main(String[] args) {

		SpringApplication.run(CardactivityApplication.class, args);
		System.out.println("Hello Word! Funcionando");

		CardDTO cardDTO = new CardDTO();
		cardDTO.setNumberCard("1234567890123456");

		String formattedCardNumber = cardDTO.getFormattedNumberCard();
		System.out.println("Número formatado: " + formattedCardNumber);
	}

}
