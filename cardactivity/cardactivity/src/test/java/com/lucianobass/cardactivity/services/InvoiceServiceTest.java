package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.repositories.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
@SpringBootTest
class InvoiceServiceTest {
    @InjectMocks
    private InvoiceService invoiceService;
    @Mock
    private InvoiceRepository invoiceRepository;

    @Test
    void GetInvoicesWithDetailsByCardIdTest() {
        //List<Invoice> simuletedInvoice = new Arra
        List<Invoice> simuletedInvoices = new ArrayList<>();
        simuletedInvoices.add(new Invoice());
        simuletedInvoices.add(new Invoice());

        when(invoiceRepository.findInvoicesWithDetailsByCardId(anyLong()))
                .thenReturn(simuletedInvoices);

        List<Invoice> result = invoiceService.getInvoicesWithDetailsByCardId(1L);
        assertThat(result).isEqualTo(simuletedInvoices);

        verify(invoiceRepository, times(1))
                .findInvoicesWithDetailsByCardId(1L);
    }

    @Test
    void createInvoiceTest() {
        Invoice invoice = new Invoice();

        invoiceService.createInvoice(invoice);
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    void getCurrentInvoiceTest() {

        Card card = new Card();
        Invoice invoice = new Invoice(card);

        when(invoiceRepository.findFirstInvoiceByCardIdCardOrderByClosingDateDesc(anyLong()))
                .thenReturn(invoice);

        Invoice result = invoiceService.getCurrentInvoice(card);
        assertThat(result).isEqualTo(invoice.getCard().getIdCard());

        verify(invoiceRepository, times(1))
                .findFirstInvoiceByCardIdCardOrderByClosingDateDesc(invoice.getCard().getIdCard());
    }

    @Test
    void getInvoicesWithDetailsByCardId_ThrowsEntityNotFoundExceptionWhenEmptyList() {
        // Configuração do mock
        Card card = new Card();
        card.setIdCard(1L); // Defina um ID válido para o Card

        Invoice invoice = new Invoice();
        invoice.setCard(card);

        when(invoiceRepository.findInvoicesWithDetailsByCardId(anyLong())).thenReturn(Collections.singletonList(invoice));

        // Execução do método sob teste
        assertThrows(EntityNotFoundException.class, () -> invoiceService.getInvoicesWithDetailsByCardId(1L));

        // Verificação se o método do repository foi chamado
        verify(invoiceRepository, times(1)).findInvoicesWithDetailsByCardId(1L);

        // Verificação se não houve outras interações com o mock
        verifyNoMoreInteractions(invoiceRepository);
    }


//    @Test
//    void getInvoicesWithDetailsByCardId_ThrowsEntityNotFoundExceptionWhenEmptyList() {
//        // Configuração do mock
//        when(invoiceRepository.findInvoicesWithDetailsByCardId(1L)).thenReturn(Collections.emptyList());
//
//        // Execução do método sob teste
//        assertThrows(EntityNotFoundException.class, () -> invoiceService.getInvoicesWithDetailsByCardId(1L));
//
//        // Verificação se o método do repository foi chamado
//        verify(invoiceRepository, times(1)).findInvoicesWithDetailsByCardId(1L);
//    }



}
