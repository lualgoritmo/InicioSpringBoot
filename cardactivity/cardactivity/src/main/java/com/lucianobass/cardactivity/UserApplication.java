package com.lucianobass.cardactivity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.lucianobass.cardactivity.model")
public class UserApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserApplication.class, args);
		System.out.println("Hello Word! Funcionando");
		//CardDTO cardDTO = new CardDTO();




//		cardDTO.setNumberCard("1234567890123456");
//
//		String formattedCardNumber = cardDTO.getFormattedNumberCard();
//		System.out.println("Número formatado: " + formattedCardNumber);
	}

}
