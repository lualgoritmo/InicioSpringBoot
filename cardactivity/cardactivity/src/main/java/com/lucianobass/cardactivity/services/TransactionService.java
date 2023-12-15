package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;
import com.lucianobass.cardactivity.exceptions.InsufficientLimitException;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private CardHolderService cardHolderService;

    public TransactionService(TransactionRepository transactionRepository, CardHolderService cardHolderService) {
        this.transactionRepository = transactionRepository;
        this.cardHolderService = cardHolderService;
    }

    @Transactional
    public Transaction createTransactionWithPurchase(Long idCardHolder, TransactionDTO transactionDTO) {
        if (idCardHolder == null || transactionDTO == null) {
            throw new IllegalArgumentException("Parâmetros inválidos para criar a transação.");
        }
        //SETAR o invoice na transaction
        Transaction transaction = ModelMapper.convertDTOToTransacation(transactionDTO);
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);

        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + idCardHolder);
        }

        transaction.setCard(cardHolder.getCard());

        Double remainingLimit = cardHolder.getCard().getCardLimit() - transaction.getPriceValue();

        if (remainingLimit < 0) {
            throw new InsufficientLimitException("Limit Insuficiente para a compra: "
                    + cardHolder.getCard().getCardLimit()
            );
        }
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

        return transactionRepository.findByCardIdCard(cardHolder.getCard().getIdCard());
    }

}
