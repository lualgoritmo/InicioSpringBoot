package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CardHolderService cardHolderService;
    @Mock
    InvoiceService invoiceService;

    @Test
    void createTransactionWithPurchaseTest() {
        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1942-10-01");
        cardHolder.getCard().setCardActive(true);
        cardHolder.setIdCardHolder(1L);
        Transaction transaction = new Transaction("Pêra", 10.0f);
        Transaction transactionOne = new Transaction("Maça", 10.0f);
        Transaction transactionTwo = new Transaction("Uva", 10.0f);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        transactions.add(transactionOne);
        transactions.add(transactionTwo);

        Invoice invoice = new Invoice(20.0f, "IN_PROGRESS", transactions,
                LocalDate.now(), LocalDate.now(), cardHolder.getCard());
        transaction.setInvoice(invoice);

        TransactionDTO transactionDTO = ModelMapper.convertTransacationToDTO(transaction);


        when(cardHolderService.getByIdCardHolder(cardHolder.getIdCardHolder())).thenReturn(cardHolder);


        when(invoiceService.getCurrentInvoice(cardHolder.getCard())).thenReturn(invoice);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction savedTransaction = transactionService.createTransactionWithPurchase(cardHolder.getIdCardHolder(), transactionDTO);

        assertThat(savedTransaction).isNotNull();

        verify(transactionRepository, times(1)).save(any(Transaction.class));

        verify(cardHolderService, times(1)).getByIdCardHolder(anyLong());
        verify(invoiceService, times(1)).getCurrentInvoice(any());
        verify(cardHolderService, times(1)).updateLimitCard(anyLong(), any());

        verifyNoMoreInteractions(transactionRepository, cardHolderService, invoiceService);
    }


//    @Test
//    void createTransactionWithPurchaseTest() {
//        // Crie um CardHolder com um Card ativo
//        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1942-10-01");
//        cardHolder.getCard().setCardActive(true);
//        cardHolder.setIdCardHolder(1L);
//
//        Transaction transaction = new Transaction("Maça", 12.0f);
//        Transaction transactionOne = new Transaction("Pèra", 12.0f);
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(transaction);
//        transactions.add(transactionOne);
//
//        transaction.setCard(cardHolder.getCard());
//
//        Invoice invoice = new Invoice(20.0f, "IN_PROGRESS", transactions,
//                LocalDate.now(), LocalDate.now(), cardHolder.getCard());
//
//        transaction.setInvoice(invoice);
//        when(cardHolderService.getByIdCardHolder(cardHolder.getIdCardHolder()))
//                .thenReturn(cardHolder);
//        when(cardHolderService.updateCardHolder(cardHolder.getIdCardHolder(),
//                cardHolder.getCard().getCardHolder())).thenReturn(any());
//        when(transactionRepository.save(transaction)).thenReturn(transaction);
//
//        verify(transactionRepository, times(1)).save(transaction);
//        verify(cardHolderService, times(1))
//                .updateCardHolder(any(),
//                any());
//
//    }

    @Test
    void getTransactionToIdCardHolderTest() {

        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        cardHolder.getCard().setCardActive(true);
        cardHolder.setIdCardHolder(1L);
        System.out.println("CARD : " + cardHolder.getCard().getNumberCard());

        Transaction transaction = new Transaction("Maça", 12.0f);
        Transaction transactionOne = new Transaction("Pêra", 18.0f);
        Transaction transactionTwo = new Transaction("Banana", 18.0f);


        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        transactions.add(transactionOne);
        transactions.add(transactionTwo);


        when(cardHolderService.getByIdCardHolder(cardHolder.getIdCardHolder())).thenReturn(cardHolder);
        when(transactionRepository.findByCardIdCard(cardHolder.getCard().getIdCard())).thenReturn(transactions);

        List<Transaction> newListTransaction =
                transactionService.getTransactionToIdCardHolder(cardHolder.getIdCardHolder());

        assertThat(newListTransaction).isNotNull();
        assertThat(newListTransaction.size()).isEqualTo(3);
        assertThat(newListTransaction.isEmpty()).isNotEqualTo(true);
        verify(transactionRepository, times(1)).findByCardIdCard(any());
        verify(cardHolderService, times(1)).getByIdCardHolder(any());

    }
}
