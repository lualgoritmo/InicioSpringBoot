package com.lucianobass.cardactivity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import com.lucianobass.cardactivity.repositories.InvoiceRepository;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.services.InvoiceService;
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

import java.time.LocalDate;
import java.util.List;

import static com.lucianobass.cardactivity.models.CardHolder.generateNumberAleatory;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class InvoiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardHolderRepository cardHolderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CardHolderService cardHolderService;

    @Autowired
    private InvoiceService invoiceService;

    //    @BeforeEach
//    void SetUpe() {
//        List<Transaction> transactions = List.of(
//                new Transaction("Pêra", 10.0f),
//                new Transaction("Maça", 10.0f),
//                new Transaction("Uva", 10.0f));
//        cardHolderRepository.deleteAll();
//        CardHolder cardHolder = cardHolderRepository.save(new CardHolder(
//                "José",
//                "11122233344",
//                "1980-07-01")
//        );
//        transactionRepository.deleteAll();
//        transactionRepository.saveAll(transactions);
//        invoiceRepository.deleteAll();
//        invoiceRepository.save(new Invoice(
//                30.0f,
//                "IN_PROGRESS",
//                transactions,
//                LocalDate.now(),
//                LocalDate.now(),
//                cardHolder.getCard()));
//    }
    @BeforeEach
    void setUp() {
        CardHolder cardHolder = new CardHolder("José", "11122233344", "1980-07-01");
        cardHolder.setIdCardHolder(1L);

        List<Transaction> transactions = List.of(
                new Transaction("Pêra", 10.0f),
                new Transaction("Maça", 10.0f),
                new Transaction("Uva", 10.0f));

        Card card = new Card(1L,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                true,
                null,
                null);

        card.setIdCard(1L);

        Invoice invoice = new Invoice(
                30.0f,
                "IN_PROGRESS",
                transactions,
                LocalDate.now(),
                LocalDate.now(),
                card);

        invoice.setIdInvoice(1L);
        cardHolder.setCard(card);

        when(cardHolderRepository.save(any(CardHolder.class))).thenReturn(cardHolder);

        when(cardHolderService.getByIdCardHolder(any(Long.class))).thenReturn(cardHolder);

        when(transactionRepository.saveAll(any())).thenReturn(transactions);

        when(transactionRepository.findByCardIdCard(any(Long.class))).thenReturn(transactions);

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        when(invoiceService.getInvoicesWithDetailsByCardId(any(Long.class))).thenReturn(List.of(invoice));

        System.out.println("cardHolder: " + cardHolder);
        System.out.println("card: " + card);
        System.out.println("invoice: " + invoice);
        System.out.println("transactions: " + transactions);

    }


    @Test
    @DisplayName("Return an invoice list")
    void getInvoices() throws Exception {

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/cards/{cardId}/invoices", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.name").value("José"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.documentNumber").value("11122233344"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.birthDate").value("01-1980-07"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.card.expiration").value("30/02"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].total").value(30.0f))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].status").value("IN_PROGRESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[0].description").value("Pêra"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[0].priceValue").value(10.0f))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[1].description").value("Maça"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[1].priceValue").value(10.0f))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[2].description").value("Uva"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[2].priceValue").value(10.0f));
    }

}
