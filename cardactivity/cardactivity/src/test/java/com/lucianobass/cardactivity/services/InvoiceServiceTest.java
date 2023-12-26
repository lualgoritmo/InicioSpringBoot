package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.repositories.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
}
