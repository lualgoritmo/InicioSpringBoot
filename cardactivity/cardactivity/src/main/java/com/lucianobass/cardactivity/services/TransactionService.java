package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.dto.TransactionDTO;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import com.lucianobass.cardactivity.repositories.TransactionRepository;
import com.lucianobass.cardactivity.util.MapperConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private CardHolderService cardHolderService;
    private CardHolderRepository cardHolderRepository;

    @Autowired
    public TransactionService(CardHolderRepository cardHolderRepository,TransactionRepository transactionRepository, CardHolderService cardHolderService) {
        this.transactionRepository = transactionRepository;
        this.cardHolderService = cardHolderService;
        this.cardHolderRepository = cardHolderRepository;
    }

    public TransactionDTO createTransaction(Long id, TransactionDTO transactionDTO) {
        CardHolder cardHolder = cardHolderService.getByIdCardHolder(id);
        Transaction transaction = MapperConvert.convertDTOToTransacation(transactionDTO);
        transaction.setCardHolder(cardHolder);
        cardHolder = cardHolderRepository.save(cardHolder);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return MapperConvert.convertTransacationToDTO(savedTransaction);
    }
}
