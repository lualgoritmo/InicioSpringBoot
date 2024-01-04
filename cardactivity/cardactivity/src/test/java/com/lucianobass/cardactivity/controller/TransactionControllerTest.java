package com.lucianobass.cardactivity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

    @Autowired
    private CardHolderRepository cardHolderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Create Transaction")
    @Transactional
    void createTransaction() throws Exception {

        CardHolder cardHolder = cardHolderRepository.save(new CardHolder("Luciano", "12345678910", "1942-10-01"));
        cardHolder.getCard().setCardActive(true);

        Transaction transaction = new Transaction();
        transaction.setDescription("Compra de Teste");
        transaction.setPriceValue(10.0f);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/{cardholderId}", cardHolder.getIdCardHolder())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction.description", Matchers.equalTo(transaction.getDescription())))
                .andExpect(jsonPath("$.transaction.priceValue", Matchers.equalTo(10.0)));
    }

}
