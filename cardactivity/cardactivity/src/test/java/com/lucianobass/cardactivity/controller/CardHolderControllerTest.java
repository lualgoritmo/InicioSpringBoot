package com.lucianobass.cardactivity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import com.lucianobass.cardactivity.services.CardHolderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static com.lucianobass.cardactivity.models.CardHolder.generateNumberAleatory;
import static com.lucianobass.cardactivity.util.ModelMapper.convertCardHolderTODTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CardHolderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CardHolderService cardHolderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("shoud create a cardholder")
    void createCardHolderTest() throws Exception {
        CardHolder cardHolder = new CardHolder();
        cardHolder.setName("Luciano");
        cardHolder.setDocumentNumber("12345678910");
        cardHolder.setBirthDate("1942-10-01");

        mockMvc.perform(MockMvcRequestBuilders.post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(convertCardHolderTODTO(cardHolder))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.equalTo(cardHolder.getName())))
                .andExpect(jsonPath("$.documentNumber", Matchers.equalTo(cardHolder.getDocumentNumber())))
                .andExpect(jsonPath("$.birthDate", Matchers.equalTo(cardHolder.getBirthDate())));

    }


    @Test
    void getAllCardHolderTest() throws Exception {
        List<CardHolder> cardHolders = Arrays.asList(
                new CardHolder("Luciano", "123456789", "1983-10-10"),
                new CardHolder("Maria", "123456789", "1963-02-12")
        );
        cardHolders.get(0).setCard(new Card(null,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                false,
                null,
                null));

        when(cardHolderService.getAllCardsHolders()).thenReturn(cardHolders);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<CardHolder> returnCardHolder = cardHolderService.getAllCardsHolders();

        assertFalse(cardHolders.isEmpty());
        assertEquals(cardHolders.size(), 2);
        assertEquals(cardHolders.get(0).getName(), returnCardHolder.get(0).getName());
        System.out.println("RESPOSTA: " + cardHolders.get(0).getName());
        System.out.println("RETORNO:" + returnCardHolder.get(0).getName());
        assertEquals(cardHolders.get(0).getDocumentNumber(), returnCardHolder.get(0).getDocumentNumber());
        assertEquals(cardHolders.get(0).getBirthDate(), returnCardHolder.get(0).getBirthDate());
        assertEquals(cardHolders.get(0).getCard().getCardCVV(), returnCardHolder.get(0).getCard().getCardCVV());
        assertEquals(cardHolders.get(0).getCard().getCardActive(), returnCardHolder.get(0).getCard().getCardActivate());
        assertEquals(cardHolders.get(0).getCard().getCardLimit(), returnCardHolder.get(0).getCard().getCardLimit());
        assertEquals(cardHolders.get(0).getCard().getCardExpiration(), returnCardHolder.get(0).getCard().getCardExpiration());
        assertEquals(cardHolders.get(0).getCard().getAvailableLimit(), returnCardHolder.get(0).getCard().getAvailableLimit());
        assertEquals(cardHolders.get(0).getCard().getTransactions(), returnCardHolder.get(0).getCard().getTransactions());
        assertEquals(cardHolders.get(0).getCard().getNumberCard(), returnCardHolder.get(0).getCard().getNumberCard());

//                .andExpect(jsonPath("$.name", Matchers.equalTo(test.get(0).getName())))
//                .andExpect(jsonPath("$[0].documentNumber", Matchers.equalTo(test.get(0).getDocumentNumber())))
//                .andExpect(jsonPath("$[0].birthDate", Matchers.equalTo("1980-07")))
//                .andExpect(jsonPath("$[0].card.numberCard", Matchers.equalTo("2745xxxxxxxx7111")))
//                .andExpect(jsonPath("$[0].card.cardExpiration", Matchers.equalTo("30/02")))
//                .andExpect(jsonPath("$[0].card.availableLimit", Matchers.equalTo("100.00")))
//                .andExpect(jsonPath("$[0].card.cardLimit", Matchers.equalTo(100.0)))
//                .andExpect(jsonPath("$[0].card.cardCVV", Matchers.equalTo("xxx")))
//                .andExpect(jsonPath("$[0].card.cardActive", Matchers.equalTo(false)))
//                .andExpect(jsonPath("$", Matchers.hasSize(1)));

//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cards")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        System.out.println(result.getResponse().getContentAsString());

    }

//    @Test
//    void createCardHolderTest() throws Exception {
//        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1942-10-01");
//        cardHolder.setIdCardHolder(1L);r
//        Card card = new Card(1L,
//                generateNumberAleatory(16),
//                "30/02",
//                "100.00",
//                100.00,
//                "123",
//                false,
//                null,
//                null
//        );
//        card.setCardHolder(cardHolder);
//        cardHolder.setCard(card);
//
//        when(cardHolderService.createCardHolder(any())).thenReturn(cardHolder);
//        when(cardHolderRepository.save(any())).thenReturn(cardHolder);
//        CardHolderDTO cardHolderDTO = ModelMapper.convertCardHolderTODTO(cardHolder);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/cards")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(cardHolderDTO)))
//                .andExpect(status().isCreated());
//        verify(cardHolderRepository, times(1)).save(any());
//        verify(cardHolderService, times(1)).createCardHolder(any());
//    }
}
