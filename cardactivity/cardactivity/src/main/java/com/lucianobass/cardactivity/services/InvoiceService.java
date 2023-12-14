package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    InvoiceRepository invoiceRepository;
    CardHolderService cardHolderService;

    @Autowired
    public InvoiceService(
            InvoiceRepository invoiceRepository,
            CardHolderService cardHolderService) {
        this.invoiceRepository = invoiceRepository;
        this.cardHolderService = cardHolderService;
    }

    @Transactional
    public Invoice createInvoice(Long idCardHolder, Invoice invoice) {
        if (idCardHolder == null || invoice == null) {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }

        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);

        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + idCardHolder);
        }

        invoice.setTransaction(cardHolder.getCard().getTransactions());
        calculateTotal(invoice);

        return invoiceRepository.save(invoice);
    }
    private void calculateTotal(Invoice invoice) {
        double total = invoice.getTransaction()
                .stream()
                .mapToDouble(Transaction::getPriceValue).sum();
        invoice.setTotal((float) total);
    }

}
