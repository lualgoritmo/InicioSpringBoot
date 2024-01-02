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
// NÃO PRECISA DESSAS CLASSES, POIS NÃO USA NO TESTE
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private CardHolderService cardHolderService;
//
//    @Autowired
//    private InvoiceService invoiceService;

    @BeforeEach
    void setup() {
        //Limpa o banco de dados para um teste não interferir em outro
        cardHolderRepository.deleteAll();
        transactionRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

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
    /*@BeforeEach
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

        --NÃO USA MOCK DENTRO DE BEFORE EACH

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

    }*/


    @Test
    @DisplayName("Return an invoice list")
    void getInvoices() throws Exception {
        //GIVEN:
        //CRIO O CARD HOLDER POIS ELE É A BASE DE TUDO E ENTÃO JÁ SALVA NO BANCO
        CardHolder cardHolder = cardHolderRepository.save(new CardHolder(
          "José",
          "11122233344",
          "1980-07-01")
        );

        //Com cardHolder criado tbm, da pra criar a invoice:
        Invoice invoice = invoiceRepository.save(
          new Invoice(
            30.0f, //Esse valor não está sendo passado para a classe invoice, pois lá dentro existe um valor fixo de zero, pode ser corrigido
            "IN_PROGRESS",
            null, //transactions, aqui estava sendo passada transactions, mas se vc olhar seu modelo, ela fica na tabela transactions e não o contrário
            LocalDate.now(),
            LocalDate.now(),
            cardHolder.getCard()
          )
        );

        //Crio minha lista de transações, note que antes estava faltando informações nas suas transaçÕes, qual era o card dela?
        Transaction transaction1 = new Transaction("Pêra",10.0f);
        Transaction transaction2 = new Transaction("Maça",10.0f);
        Transaction transaction3 = new Transaction("Uva",10.0f);
        //Faz o set dos card para dentro da transaction (isso é uma melhoria, essa construção poderia ficar dentro do construtor)
        transaction1.setCard(cardHolder.getCard());
        transaction2.setCard(cardHolder.getCard());
        transaction3.setCard(cardHolder.getCard());
        //Seta qual é a invoice dessa transaction, também poderia ficar dentro do construtor ... o ideal é evitar o uso do set
        transaction1.setInvoice(invoice);
        transaction1.setInvoice(invoice);
        transaction1.setInvoice(invoice);

        //Como no invoice o valor que vc passou (30) não cria a fatura com aquele valor, aqui vc seta o valor certo da invoice
        invoice.setTotal(30.0F); //Essa info é salva oeki transaction.save devido ao cascade = ALL

        //Cria a lista de transações
        List<Transaction> transactions = List.of(
          transaction1,
          transaction2,
          transaction3
        );

        //Após criar, salva tudo no banco
        transactionRepository.saveAll(transactions);

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
