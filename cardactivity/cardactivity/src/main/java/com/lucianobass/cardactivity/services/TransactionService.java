package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;
import com.lucianobass.cardactivity.exceptions.InsufficientLimitException;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardHolderService cardHolderService;
    private final InvoiceService invoiceService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CardHolderService cardHolderService,
                              InvoiceService invoiceService) {
        this.transactionRepository = transactionRepository;
        this.cardHolderService = cardHolderService;
        this.invoiceService = invoiceService;
    }

    @Transactional
    public Transaction createTransactionWithPurchase(Long idCardHolder, TransactionDTO transactionDTO) {
        if (idCardHolder == null || transactionDTO == null) {
            throw new IllegalArgumentException("Parâmetros inválidos para criar a transação.");
        }

        // SETAR o invoice na transaction
        Transaction transaction = ModelMapper.convertDTOToTransacation(transactionDTO);
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);

        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + idCardHolder);
        }

        transaction.setCard(cardHolder.getCard());

        double remainingLimit = cardHolder.getCard().getCardLimit() - transaction.getPriceValue();

        if (remainingLimit < 0) {
            throw new InsufficientLimitException("Limit Insuficiente para a compra: " + cardHolder.getCard().getCardLimit());
        }

        // BUSCOU A FATURA ATUAL
        Invoice invoice = invoiceService.getCurrentInvoice(cardHolder.getCard());

        // SE NÃO EXISTE, PRECISA CRIAR A FATURA
        if (invoice == null) {
            invoice = new Invoice(cardHolder.getCard());
            invoiceService.createInvoice(invoice);  // Salve a instância de Invoice primeiro
        }

        // INCREMENTAR O VALOR DA FATURA ATUAL (TOTAL + VALOR DA TRANSAÇÃO)
        Float newTotal = invoice.getTotal() + transaction.getPriceValue();

        invoice.setTotal(newTotal);

        // SETAR A INVOICE DENTRO DA TRANSAÇÃO (transaction.set)
        transaction.setInvoice(invoice);

        transactionRepository.save(transaction);
        cardHolder.getCard().setCardLimit(remainingLimit);
        cardHolderService.updateLimitCard(idCardHolder, cardHolder.getCard());

        return transaction;
    }



    @Transactional
    public List<Transaction> getTransactionToIdCardHolder(Long idCardHolder) {
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);

        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + idCardHolder);
        }
        List<Transaction> transactions = transactionRepository.findByCardIdCard(cardHolder.getCard().getIdCard());
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }
        return transactions;
    }

}
