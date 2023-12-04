package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.lucianobass.cardactivity.models.Card.generateNumberCard;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardHolderServiceTest {
    @Autowired
    CardHolderService cardHolderService = new CardHolderService();

    @Test
    void testCreateCard() {
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        cardHolder.setCard(new Card(null,
                generateNumberCard(16),
                "30/02",
                "100.00",
                "100.00",
                "123",
                false,
                null));
        CardHolderDTO cardHolderDTO = cardHolderService.convertToResponseDTO(cardHolder);
        System.out.println("ID antes de salvar: " + cardHolderDTO.getId());
        cardHolderService.createCard(cardHolderDTO);
        assertEquals(cardHolder.getName(), cardHolderDTO.getName());
        assertEquals(cardHolder.getDocumentNumber(), cardHolderDTO.getDocumentNumber());
        assertEquals(cardHolder.getBirthDate(), cardHolderDTO.getBirthDate());
        assertNotEquals(cardHolder.generateNumberCard(16).replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "x"),
                cardHolderDTO.getCard().getNumberCard());
        assertEquals(cardHolder.getCard().getCardLimit(), cardHolderDTO.getCard().getCardLimit());
        assertEquals(cardHolder.generateNumberCard(3).replaceAll("(\\d)",
                "x"), cardHolderDTO.getCard().getCardCVV());
        assertEquals(cardHolder.getCard().getCardExpiration(), cardHolderDTO.getCard().getCardExpiration());
    }

    @Test
    void testGetCardHolderById() {
        // Arrange
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        CardHolderDTO savedCardHolder = cardHolderService.createCard(cardHolderService.convertToResponseDTO(cardHolder));

        // Imprimir informações úteis
        System.out.println("ID salvo: " + savedCardHolder.getId());

        // Act
        CardHolderDTO retrievedCardHolder = cardHolderService.getByIdCardHolder(savedCardHolder.getId());

        // Imprimir informações úteis
        System.out.println("ID recuperado: " + retrievedCardHolder.getId());

        // Assert
        assertNotNull(retrievedCardHolder.getId(), "O ID não deveria ser nulo ao buscar por ID");
        assertNotNull(savedCardHolder.getId(), "O ID não deveria ser nulo após a criação");
        assertEquals(savedCardHolder.getId(), retrievedCardHolder.getId(), "O ID retornado deve ser igual ao ID salvo");

    }

    @Test
    void testGetAllCardsHolders() {
        //Arrange
        CardHolder cardHolderOne = new CardHolder("Luciano", "123456789", "1983-10-10");
        CardHolder cardHolderTwo = new CardHolder("Maria", "123456789", "1963-02-12");
        CardHolderDTO cardHolderDTOOne = cardHolderService.convertToResponseDTO(cardHolderOne);
        CardHolderDTO cardHolderDTOTwo = cardHolderService.convertToResponseDTO(cardHolderTwo);
        //ACT
        cardHolderService.createCard(cardHolderDTOOne);
        cardHolderService.createCard(cardHolderDTOTwo);
        List<CardHolderDTO> listDTO = cardHolderService.getAllCardsHolders();
        CardHolderDTO firstDTO = listDTO.get(0);
        //ASSERT DTO
        assertFalse(listDTO.contains(cardHolderDTOOne));
        assertEquals("Luciano", firstDTO.getName());
        assertEquals(cardHolderOne.getDocumentNumber(), firstDTO.getDocumentNumber());
        assertEquals(cardHolderOne.getBirthDate(), firstDTO.getBirthDate());
        //ASSERT
        //assertNotNull(cardHolderDTOOne.getId(), "O ID não deveria ser nulo após a criação do cartão.");
        assertEquals(2, listDTO.size());
        assertFalse(listDTO.size() == 0, "É falso");
        assertTrue(listDTO.size() > 0, "É verdadeiro");
        assertFalse(listDTO.isEmpty());
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
        CardHolderDTO savedCardHolder = cardHolderService.createCard(cardHolderService.convertToResponseDTO(cardHolder));

        // Modifique alguns atributos para simular uma atualização
        String newName = "Luciano Updated";
        String newDocumentNumber = "987654321";
        String newBirthDate = "1990-01-01";

        CardHolderDTO updatedCardHolderDTO = new CardHolderDTO();
        updatedCardHolderDTO.setName(newName);
        updatedCardHolderDTO.setDocumentNumber(newDocumentNumber);
        updatedCardHolderDTO.setBirthDate(newBirthDate);

        // Act
        cardHolderService.updateCardHolder(savedCardHolder.getId(), updatedCardHolderDTO);

        // Assert
        // Obtenha o CardHolder após a atualização
        CardHolderDTO retrievedCardHolder = cardHolderService.getByIdCardHolder(savedCardHolder.getId());

        // Verifique se os atributos foram atualizados corretamente
        assertEquals(newName, retrievedCardHolder.getName(), "O nome não foi atualizado corretamente");
        assertEquals(newDocumentNumber, retrievedCardHolder.getDocumentNumber(), "O número do documento não foi atualizado corretamente");
        assertEquals(newBirthDate, retrievedCardHolder.getBirthDate(), "A data de nascimento não foi atualizada corretamente");
    }


//    @Test
//    void testDeleteIdCard() {
//        // Arrange
//        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
//        CardHolderDTO[] savedCardHolder = { cardHolderService.createCard(cardHolderService.convertToResponseDTO(cardHolder)) };
//
//        // Verifique se o ID não é nulo antes de chamar deleteIdCard
//        assertNotNull(savedCardHolder[0].getId(), "O ID não deveria ser nulo após a criação");
//
//        // Imprima informações úteis para diagnóstico
//        System.out.println("ID do CardHolder após a criação: " + savedCardHolder[0].getId());
//
//        // Agora, recupere novamente o CardHolder após a criação para garantir que o ID seja gerado
//        savedCardHolder[0] = cardHolderService.getByIdCardHolder(savedCardHolder[0].getId());
//
//        // Act
//        try {
//            cardHolderService.deleteIdCard(savedCardHolder[0].getId());
//        } catch (Exception e) {
//            // Se for lançada uma exceção, falhe o teste
//            fail("Não deveria lançar exceção ao excluir um CardHolder existente. Exceção: " + e.getMessage());
//        }
//
//        // Assert
//        assertThrows(CardNotFoundExceptions.class, () -> {
//            cardHolderService.getByIdCardHolder(savedCardHolder[0].getId());
//        }, "Deveria lançar exceção de CardNotFoundExceptions após excluir o CardHolder");
//
//        // Tente excluir novamente para garantir que não lance uma exceção diferente
//        assertDoesNotThrow(() -> {
//            cardHolderService.deleteIdCard(savedCardHolder[0].getId());
//        }, "Não deveria lançar exceção após tentar excluir um CardHolder inexistente");
//    }

}
