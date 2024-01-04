package com.lucianobass.cardactivity.controller;

import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import com.lucianobass.cardactivity.repositories.InvoiceRepository;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
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
import java.time.LocalDate;
import java.util.List;

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
    private EntityManager entityManager;
    @BeforeEach
    void setup() {
        //Limpa o banco de dados para um teste não interferir em outro
        cardHolderRepository.deleteAll();
        transactionRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

    @Test
    @DisplayName("Return an invoice list")
    @Transactional
    void getInvoicesTest() throws Exception {
        // GIVEN:
        // CRIO O CARD HOLDER POIS ELE É A BASE DE TUDO E ENTÃO JÁ SALVA NO BANCO
        CardHolder cardHolder = cardHolderRepository.save(new CardHolder(
                "José",
                "11122233344",
                "1980-07-01")
        );

        List<Transaction> transactions = List.of(
                new Transaction("Pêra", 10.0f),
                new Transaction("Maça", 10.0f),
                new Transaction("Uva", 10.0f)
        );

        // Com cardHolder criado também, dá para criar a invoice:
        Invoice invoice = invoiceRepository.save(
                new Invoice(
                        30.0f,
                        "IN_PROGRESS",
                        transactions,
                        LocalDate.now(),
                        LocalDate.now(),
                        cardHolder.getCard()
                )
        );
        entityManager.persist(invoice);
        // Crio minha lista de transações, note que antes estava faltando informações nas suas transações, qual era o card dela?
        Transaction transaction1 = new Transaction("Pêra", 10.0f);
        Transaction transaction2 = new Transaction("Maça", 10.0f);
        Transaction transaction3 = new Transaction("Uva", 10.0f);

        // Faz o set dos card para dentro da transaction (isso é uma melhoria, essa construção poderia ficar dentro do construtor)
        transaction1.setCard(cardHolder.getCard());
        transaction2.setCard(cardHolder.getCard());
        transaction3.setCard(cardHolder.getCard());

        // Seta qual é a invoice dessa transaction, também poderia ficar dentro do construtor ... o ideal é evitar o uso do set
        transaction1.setInvoice(invoice);
        transaction2.setInvoice(invoice);
        transaction3.setInvoice(invoice);

        // Como no invoice o valor que você passou (30) não cria a fatura com aquele valor, aqui você seta o valor certo da invoice
        invoice.setTotal(30.0f); // Essa info é salva pelo transaction.save devido ao cascade = ALL

        // Cria a lista de transações
        List<Transaction> transactionList = List.of(
                transaction1,
                transaction2,
                transaction3
        );

        // Após criar, salva tudo no banco
        transactionRepository.saveAll(transactionList);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/cards/{cardId}/invoices", cardHolder.getCard().getIdCard())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.name").value("José"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.documentNumber").value("11122233344"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.birthDate").value("1980-07-01"))
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


//    @Test
//    @DisplayName("Return an invoice list")
//    void getInvoices() throws Exception {
//        //GIVEN:
//        //CRIO O CARD HOLDER POIS ELE É A BASE DE TUDO E ENTÃO JÁ SALVA NO BANCO
//        CardHolder cardHolder = cardHolderRepository.save(new CardHolder(
//          "José",
//          "11122233344",
//          "1980-07-01")
//        );
//
//        //Com cardHolder criado tbm, da pra criar a invoice:
//        Invoice invoice = invoiceRepository.save(
//          new Invoice(
//            30.0f, //Esse valor não está sendo passado para a classe invoice, pois lá dentro existe um valor fixo de zero, pode ser corrigido
//            "IN_PROGRESS",
//            null, //transactions, aqui estava sendo passada transactions, mas se vc olhar seu modelo, ela fica na tabela transactions e não o contrário
//            LocalDate.now(),
//            LocalDate.now(),
//            cardHolder.getCard()
//          )
//        );
//
//        //Crio minha lista de transações, note que antes estava faltando informações nas suas transaçÕes, qual era o card dela?
//        Transaction transaction1 = new Transaction("Pêra", 10.0f);                                                                                                                                      "Pêra",10.0f);
//        transaction1.setIdTransaction(1L);
//        Transaction transaction2 = new Transaction("Maça",10.0f);
//        transaction2.setIdTransaction(2L);
//        Transaction transaction3 = new Transaction("Uva",10.0f);
//        transaction3.setIdTransaction(3L);
//        //Faz o set dos card para dentro da transaction (isso é uma melhoria, essa construção poderia ficar dentro do construtor)
//        transaction1.setCard(cardHolder.getCard());
//        transaction2.setCard(cardHolder.getCard());
//        transaction3.setCard(cardHolder.getCard());
//        //Seta qual é a invoice dessa transaction, também poderia ficar dentro do construtor ... o ideal é evitar o uso do set
//        transaction1.setInvoice(invoice);
//        transaction1.setInvoice(invoice);
//        transaction1.setInvoice(invoice);
//
//        //Como no invoice o valor que vc passou (30) não cria a fatura com aquele valor, aqui vc seta o valor certo da invoice
//        invoice.setTotal(30.0f); //Essa info é salva oeki transaction.save devido ao cascade = ALL
//
//        //Cria a lista de transações
//        List<Transaction> transactions = List.of(
//          transaction1,
//          transaction2,
//          transaction3
//        );
//
//        //Após criar, salva tudo no banco
//        transactionRepository.saveAll(transactions);
//
//        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/cards/{cardId}/invoices", cardHolder.getCard().getIdCard())
//                .contentType(MediaType.APPLICATION_JSON));
//
//        result.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.name").value("José"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.documentNumber").value("11122233344"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cardHolder.birthDate").value("01-1980-07"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.card.expiration").value("30/02"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].total").value(30.0f))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].status").value("IN_PROGRESS"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[0].description").value("Pêra"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[0].priceValue").value(10.0f))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[1].description").value("Maça"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[1].priceValue").value(10.0f))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[2].description").value("Uva"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.invoices[0].transactions[2].priceValue").value(10.0f));
//    }

}
