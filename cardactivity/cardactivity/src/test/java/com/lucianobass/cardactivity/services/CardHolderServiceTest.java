package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static com.lucianobass.cardactivity.models.CardHolder.generateNumberCard;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardHolderServiceTest {
    @Autowired
    CardHolderService cardHolderService;

    @Test
    void testCreateCard() {
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        cardHolder.setCard(new Card(null,
               generateNumberCard(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                false,
                null,
                null));
        cardHolder = cardHolderService.getByIdCardHolder(cardHolder.getIdCardHolder());
        System.out.println("VER SE O ID NÃO É NULO: " + cardHolder.getIdCardHolder());
        cardHolderService.createCard(cardHolder);
        assertEquals(cardHolder.getName(), cardHolder.getName());
        assertEquals(cardHolder.getDocumentNumber(), cardHolder.getDocumentNumber());
        assertEquals(cardHolder.getBirthDate(), cardHolder.getBirthDate());
        assertNotEquals(generateNumberCard(16).replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x"),
                cardHolder.getCard().getNumberCard());
        assertEquals(cardHolder.getCard().getCardLimit(), cardHolder.getCard().getCardLimit());
        assertEquals(generateNumberCard(3).replaceAll("(\\d)",
                "x"), cardHolder.getCard().getCardCVV());
        assertEquals(cardHolder.getCard().getCardExpiration(), cardHolder.getCard().getCardExpiration());
    }

    @Test
    void testGetCardHolderById() {
        // Arrange
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        CardHolder savedCardHolder = cardHolderService.createCard(cardHolder);

        // Imprimir informações úteis
        System.out.println("ID salvo: " + savedCardHolder.getIdCardHolder());

        // Act
        CardHolder retrievedCardHolder = cardHolderService.getByIdCardHolder(savedCardHolder.getIdCardHolder());

        // Imprimir informações úteis
        System.out.println("ID recuperado: " + retrievedCardHolder.getIdCardHolder());

        // Assert
        assertNotNull(retrievedCardHolder.getIdCardHolder(), "O ID não deveria ser nulo ao buscar por ID");
        assertNotNull(savedCardHolder.getIdCardHolder(), "O ID não deveria ser nulo após a criação");
        assertEquals(savedCardHolder.getIdCardHolder(), retrievedCardHolder.getIdCardHolder(), "O ID retornado deve ser igual ao ID salvo");

    }

    @Test
    void testGetAllCardsHolders() {
        //Arrange
        CardHolder cardHolderOne = new CardHolder("Luciano", "123456789", "1983-10-10");
        CardHolder cardHolderTwo = new CardHolder("Maria", "123456789", "1963-02-12");
        //ACT
        cardHolderService.createCard(cardHolderOne);
        cardHolderService.createCard(cardHolderTwo);
        List<CardHolder> listCardHolder = cardHolderService.getAllCardsHolders();
        CardHolder firstOne = listCardHolder.get(0);
        //ASSERT DTO
        assertFalse(listCardHolder.contains(cardHolderOne));
        assertEquals("Luciano", firstOne.getName());
        assertEquals(cardHolderOne.getDocumentNumber(), firstOne.getDocumentNumber());
        assertEquals(cardHolderOne.getBirthDate(), firstOne.getBirthDate());
        //ASSERT
        //assertNotNull(cardHolderDTOOne.getId(), "O ID não deveria ser nulo após a criação do cartão.");
        assertEquals(3, listCardHolder.size());
        assertFalse(listCardHolder.isEmpty(), "É falso");
        assertTrue(listCardHolder.size() > 0, "É verdadeiro");
        assertFalse(listCardHolder.isEmpty());
        //assertTrue(listDTO.isEmpty());
    }

    //    @Test
//    void testGetByIdCardHolder() {
//        //Arrange
//        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
//        CardHolderDTO cardHolderDTO = cardHolderService.convertToResponseDTO(cardHolder);
//        CardHolderDTO cardDTO = cardHolderService.createCard(cardHolderDTO);
//        //ACT
//        CardHolderDTO retrievedCardHolderDTO = cardHolderService.getByIdCardHolder(cardHolderDTO.getId());
//        //ASSERT
//        assertNotNull(retrievedCardHolderDTO, "O CardHolderDTO recuperado não deveria ser nulo");
//        assertEquals(cardHolderDTO.getId(), retrievedCardHolderDTO.getId(), "Os IDs deveriam ser iguais");
//    }

    @Test
    void testUpdateCardHolder() {
        // Arrange
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        CardHolder savedCardHolder = cardHolderService.createCard(cardHolder);

        String newName = "Luciano UPDATE";
        String newDocumentNumber = "00000000000";
        String newBirthDate = "1983-10-10";

        CardHolder cardHolderUpdate = new CardHolder();
        cardHolderUpdate.setName(newName);
        cardHolderUpdate.setDocumentNumber(newDocumentNumber);
        cardHolderUpdate.setBirthDate(newBirthDate);

        // Act
        cardHolderService.updateCardHolder(savedCardHolder.getIdCardHolder(), cardHolderUpdate);

        // Assert
        CardHolder retrievedCardHolder = cardHolderService.getByIdCardHolder(savedCardHolder.getIdCardHolder());

        assertEquals(newName, retrievedCardHolder.getName(), "O nome não foi atualizado corretamente");
        assertEquals(newDocumentNumber, retrievedCardHolder.getDocumentNumber(), "O número do documento não foi atualizado corretamente");
        assertEquals(newBirthDate, retrievedCardHolder.getBirthDate(), "A data de nascimento não foi atualizada corretamente");
    }


    @Test
    void testDeleteIdCard() {
       // Arrange
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        CardHolder savedCardHolder = cardHolderService.createCard(cardHolder);

        assertNotNull(savedCardHolder.getIdCardHolder(), "O ID não deveria ser nulo após a criação");

        savedCardHolder= cardHolderService.getByIdCardHolder(savedCardHolder.getIdCardHolder());
        System.out.println(savedCardHolder.getIdCardHolder() + "IDCARDHOLDER");
        // Act
        try {
            cardHolderService.deleteIdCard(savedCardHolder.getIdCardHolder());
        } catch (Exception e) {
            fail("Não deveria lançar exceção ao excluir um CardHolder existente. Exceção: " + e.getMessage());
        }

       // Assert
        CardHolder finalSavedCardHolder = savedCardHolder;
        assertThrows(CardNotFoundExceptions.class, () -> {
            cardHolderService.getByIdCardHolder(finalSavedCardHolder.getIdCardHolder()); },
                "Deveria lançar exceção de CardNotFoundExceptions após excluir o CardHolder");

       assertDoesNotThrow(() -> {
           cardHolderService.deleteIdCard(finalSavedCardHolder.getIdCardHolder());
       }, "Não deveria lançar exceção após tentar excluir um CardHolder inexistente");
   }
}
