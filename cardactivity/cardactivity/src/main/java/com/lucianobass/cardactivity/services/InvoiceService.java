package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class InvoiceService {
    InvoiceRepository invoiceRepository;
    TransactionService transactionService;

    @Autowired
    public InvoiceService(
            InvoiceRepository invoiceRepository, TransactionService transactionService) {
        this.invoiceRepository = invoiceRepository;
        this.transactionService = transactionService;
    }

    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice não encontrado para o ID: " + invoiceId));
    }

    @Transactional
    public Long createInvoice(Long idCardHolder) {
        List<Transaction> transactions = transactionService.getTransactionToIdCardHolder(idCardHolder);

        if (transactions.isEmpty()) {
            System.out.println("Está vazio");
            return null;
        }

        Invoice invoice = new Invoice();
        invoice.setTransactions(transactions);
        calculateTotal(invoice);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return savedInvoice.getIdInvoice();
    }
//    @Transactional
//    public Long createInvoice(Long idCardHolder) {
//        List<Transaction> transactions = transactionService.getTransactionToIdCardHolder(idCardHolder);
//
//        if (transactions.isEmpty()) {
//            System.out.println("Está vazio");
//            return null;
//        }
//
//        Invoice invoice = new Invoice();
//        invoice.setTransactions(transactions);
//        calculateTotal(invoice);
//
//        Invoice savedInvoice = invoiceRepository.save(invoice);
//        return savedInvoice.getIdInvoice();
//    }

    private void calculateTotal(Invoice invoice) {
        double total = invoice.getTransactions().stream().mapToDouble(Transaction::getPriceValue).sum();
        invoice.setTotal((float) total);
    }

}

