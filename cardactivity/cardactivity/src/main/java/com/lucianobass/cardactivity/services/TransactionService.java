package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.dto.TransactionDTO;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.util.MapperConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardHolderService cardHolderService;

    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            CardHolderService cardHolderService) {
        this.transactionRepository = transactionRepository;
        this.cardHolderService = cardHolderService;
    }

    public Transaction createTransaction(Long id, TransactionDTO transactionDTO) {
        if (id == null || transactionDTO == null) {
            throw new IllegalArgumentException("ID do cardholder ou Transação inválido");
        }
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(id);
        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + id);
        }
        Transaction transaction = new Transaction();
        transaction = MapperConvert.convertDTOToTransacation(transactionDTO);
        transaction.setCard(cardHolder.getCard());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction;
    }

//        @Transactional
//    public Transaction getByTransacationId(@PathVariable Long id) {
//            CardHolder cardHolder = cardHolderService.getByIdCardHolder(id);
//
//        Transaction transaction = transactionRepository.findById();
//        Card card = transaction.getCard();
//
//
//        return transaction;
//    }
//    @Transactional
//    public Transaction getByTransacationDocumentNumber(String documentNumber) {
//        Optional<Transaction> optionalTransaction = transactionRepository.findByDocumentNumber(documentNumber);
//
//        Transaction transaction = optionalTransaction.orElseThrow(() -> new TransactionNotFoundException(documentNumber));
//
//        Card card = transaction.getCard();
//        CardHolder cardHolder = card.getCardHolder();
//
//
//        String cardNumber = card.getNumberCard();
//        String cardExpiration = card.getCardExpiration();
//        String cardCVV = card.getCardCVV();
//
//        String cardHolderName = cardHolder.getName();
//        String cardHolderDocumentNumber = cardHolder.getDocumentNumber();
//
//        return transaction;
//    }


    @Transactional
    public List<Transaction> getTransactionToIdCardHolder(Long idCardHolder) {
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);
        List<Transaction> transactions = transactionRepository.findByCardId(cardHolder.getCard().getId());
        // List<Transaction> transactions = transactionRepository.findByCardId(cardHolder.getId());
        return transactions;
    }

}
