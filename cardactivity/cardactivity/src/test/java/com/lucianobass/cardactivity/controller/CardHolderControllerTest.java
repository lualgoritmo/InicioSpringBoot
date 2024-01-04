package com.lucianobass.cardactivity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import com.lucianobass.cardactivity.services.CardHolderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static com.lucianobass.cardactivity.models.CardHolder.generateNumberAleatory;
import static com.lucianobass.cardactivity.util.ModelMapper.convertCardHolderTODTO;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CardHolderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CardHolderRepository cardHolderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CardHolderService cardHolderService;

    @BeforeEach
    void setup() {
        cardHolderRepository.deleteAll();
    }
    @Autowired
    EntityManager entityManager;
    @Test
    @DisplayName("shoud create a cardholder")
    void createCardHolderTest() throws Exception {
        CardHolder cardHolder = new CardHolder("Luciano", "123456313214", "1980-07-01");

        mockMvc.perform(MockMvcRequestBuilders.post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(convertCardHolderTODTO(cardHolder))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.equalTo(cardHolder.getName())))
                .andExpect(jsonPath("$.documentNumber", Matchers.equalTo(cardHolder.getDocumentNumber())))
                .andExpect(jsonPath("$.birthDate", Matchers.equalTo(cardHolder.getBirthDate())));

    }

    @Test
    @Transactional
    @DisplayName("search all card holders\n")
    void getAllCardHolderTest() throws Exception {

        List<CardHolder> cardHolders = List.of(
                new CardHolder("José", "00000000000", "1980-07-01"),
                new CardHolder("Maria", "11122233344", "1980-07-01"),
                new CardHolder("Solange", "11111111111", "1980-07-01")
        );

        cardHolderRepository.saveAll(cardHolders);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/cards")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo(cardHolders.get(0).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].documentNumber", Matchers.equalTo(cardHolders.get(0).getDocumentNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthDate", Matchers.equalTo(cardHolders.get(0).getBirthDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.equalTo(cardHolders.get(1).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].documentNumber", Matchers.equalTo(cardHolders.get(1).getDocumentNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].birthDate", Matchers.equalTo(cardHolders.get(1).getBirthDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.equalTo(cardHolders.get(2).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].documentNumber", Matchers.equalTo(cardHolders.get(2).getDocumentNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].birthDate", Matchers.equalTo(cardHolders.get(2).getBirthDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.equalTo(3)));
    }

    @Test
    @Transactional
    @DisplayName("search card holder with document number")
    void getByCardHolderDocumentNumberTest() throws Exception {
        CardHolder cardHolder = new CardHolder("José", "00000000000", "1980-07-01");
        cardHolderRepository.save(cardHolder);

        //cardHolderService.getByCardHolderDocumentNumber(cardHolder.getDocumentNumber());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/cards/cardholder/{documentNumber}", cardHolder.getDocumentNumber())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("José"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documentNumber").value("00000000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("1980-07-01"));
    }

    @Test
    @Transactional
    @DisplayName("Atualiza CardHolder")
    void updateCardHolderTest() throws Exception {
        CardHolder cardHolder = new CardHolder("José", "00000000000", "1980-07-01");
        cardHolderRepository.save(cardHolder);

        Card card = new Card(1L,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                false,
                null,
                null);

        card.setCardHolder(cardHolder);
        cardHolder.setCard(card);

        cardHolder.getCard().setCardActive(true);
        cardHolder.setName("Mariola");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(
                        "/cards/{id}/update", cardHolder.getIdCardHolder())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.equalTo(cardHolder.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documentNumber", Matchers.equalTo(cardHolder.getDocumentNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate", Matchers.equalTo(cardHolder.getBirthDate())));
    }

}
