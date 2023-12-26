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
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;

    }

//    public List<Invoice> getInvoicesByCardId(Long cardId) {
//        List<Invoice> invoices =
//                invoiceRepository.findInvoicesWithDetailsByCardId(cardId);
//        if (invoices.isEmpty()) {
//            throw new EntityNotFoundException("Lista Vazia, SERVICE Invoice");
//        }
//        return invoices;
//    }

    @Transactional
    public List<Invoice> getInvoicesWithDetailsByCardId(Long cardId) {
        List<Invoice> invoices = invoiceRepository.findInvoicesWithDetailsByCardId(cardId);
        if (invoices.isEmpty()) {
            throw new EntityNotFoundException("Lista Vazia, SERVICE Invoice");
        }
        return invoices;

    }

    @Transactional
    public Invoice getCurrentInvoice(Card card) {
        return invoiceRepository.findFirstInvoiceByCardIdCardOrderByClosingDateDesc(card.getIdCard());
    }

    @Transactional
    public void createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    private void calculateTotal(Invoice invoice) {
        Float total = (float) invoice.getTransactions().stream().mapToDouble(Transaction::getPriceValue).sum();
        invoice.setTotal(total);
    }

}
