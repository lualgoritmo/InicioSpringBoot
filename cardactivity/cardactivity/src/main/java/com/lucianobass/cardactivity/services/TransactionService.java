package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.dto.*;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.util.MapperConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
        Transaction transaction;
        transaction = MapperConvert.convertDTOToTransacation(transactionDTO);
        transaction.setCard(cardHolder.getCard());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction;
    }

    @Transactional
    public ListTransactionDTO getTransactionToIdCardHolder(Long idCardHolder) {
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);

        if (cardHolder == null) {
            // Lidar com o caso em que cardHolder não foi encontrado
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + idCardHolder);
        }

        List<Transaction> transactions = transactionRepository.findByCardId(cardHolder.getCard().getId());

        CardHolderTransactionDTO cardHolderDTO = new CardHolderTransactionDTO(
                cardHolder.getName(),
                cardHolder.getDocumentNumber(),
                cardHolder.getBirthDate());

        CardTransactionDTO cardDTO = new CardTransactionDTO(
                cardHolder.getCard().getNumberCard(),
                cardHolder.getCard().getCardExpiration(),
                cardHolder.getCard().getCardCVV());

        List<TransactionDTOInvoice> transactionDTOList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionDTOInvoice transactionDTOInvoice = new TransactionDTOInvoice(
                    transaction.getDescription(),
                    transaction.getPriceValue(),
                    transaction.getTransactionTime());
            transactionDTOList.add(transactionDTOInvoice);
        }

        ListTransactionDTO listTransactionDTOInvoice = new ListTransactionDTO(
                cardHolderDTO,
                cardDTO,
                transactionDTOList);

        return listTransactionDTOInvoice;
    }

}
