package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.transactionDTO.*;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
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

    @Transactional
    public Transaction createTransactionWithPurchase(Long idCardHolder, TransactionDTO transactionDTO) {
        if (idCardHolder == null || transactionDTO == null) {
            throw new IllegalArgumentException("Parâmetros inválidos para criar a transação.");
        }
        Transaction transaction = ModelMapper.convertDTOToTransacation(transactionDTO);
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);
        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + idCardHolder);
        }

        Double remainingLimit = cardHolder.getCard().getCardLimit() - transaction.getPriceValue();
        if(remainingLimit <= 0) {
            throw new EntityNotFoundException("Limit Insuficiente para a compra: " + cardHolder.getCard().getCardLimit() );
        }

        Transaction transacationReturn = ModelMapper.convertDTOToTransacation(transactionDTO);
        transacationReturn.setCard(cardHolder.getCard());

        cardHolder.getCard().setCardLimit(remainingLimit);

        transactionRepository.save(transacationReturn);
        cardHolderService.updateLimitCard(idCardHolder, cardHolder.getCard());

        return transacationReturn;
    }

    @Transactional
    public ListTransactionDTO getTransactionToIdCardHolder(Long idCardHolder) {
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);

        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o ID: " + idCardHolder);
        }

        List<Transaction> transactions = transactionRepository.findByCardIdCard(cardHolder.getCard().getIdCard());

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
