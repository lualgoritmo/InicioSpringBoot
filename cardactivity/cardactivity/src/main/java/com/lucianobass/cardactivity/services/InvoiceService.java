package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.models.Card;
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
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(
            InvoiceRepository invoiceRepository)
    {
        this.invoiceRepository = invoiceRepository;

    }

    public List<Invoice> getInvoicesByCardId(Long cardId) {
        List<Invoice> invoices =
                invoiceRepository.findInvoicesWithDetailsByCardId(cardId);
        if (invoices.isEmpty()) {
            throw new EntityNotFoundException("Lista Vazia, SERVICE Invoice");
        }
        return invoices;
    }

    @Transactional
    public List<Invoice> getInvoicesWithDetailsByCardId(Long cardId) {
        List<Invoice> invoices = invoiceRepository.findInvoicesWithDetailsByCardId(cardId);
        if (invoices.isEmpty()) {
            throw new EntityNotFoundException("Lista Vazia, SERVICE Invoice");
        }
        return invoices;
//        return invoices.stream()
//                .map(ModelMapper::convertInvoiceTODTO)
//                .collect(Collectors.toList());
    }

    @Transactional
    public Invoice getCurrentInvoice(Card card) {
        return invoiceRepository.findFirstInvoiceByCardIdCardOrderByClosingDateDesc(card.getIdCard());
    }

    @Transactional
    public void createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

//    public List<Invoice> getInvoices(Long idCard) {
//        if(idCard == null) {
//            System.out.println("Não existe idCard, SERVICE!");
//            throw new EntityNotFoundException("Não existe id, SERVICE Invoice");
//        }
//        List<Invoice> invoices = invoiceRepository.findByIdInvoice(idCard);
//        if(invoices.isEmpty()) {
//            throw new EntityNotFoundException("Lista Vazia, SERVICE Invoice");
//        }
//        return invoices;
//    }
//
//    private void calculateTotal(Invoice invoice) {
//        double total = invoice.getTransactions().stream().mapToDouble(Transaction::getPriceValue).sum();
//        invoice.setTotal((float) total);
//    }

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
        Float total = (float) invoice.getTransactions().stream().mapToDouble(Transaction::getPriceValue).sum();
        invoice.setTotal(total);
    }

}
