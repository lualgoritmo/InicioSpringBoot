package com.lucianobass.cardactivity.controllerresources.controller;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.*;
import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.services.TransactionService;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.lucianobass.cardactivity.util.ModelMapper.convertToResponseDTO;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{cardholderId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TransactionResponseDTO createTransaction(@PathVariable Long cardholderId,
                                                    @RequestBody TransactionDTO transactionDTO) {
        if (cardholderId == null || transactionDTO == null) {
            throw new IllegalArgumentException("ID do cardholder ou Transação inválido");
        }

        try {

            Transaction createTransaction = transactionService.createTransactionWithPurchase(cardholderId, transactionDTO);
            System.out.println("Transação criada com sucesso.");
            Double purchaseAmount = createTransaction.getCard().getCardLimit();
            if(purchaseAmount <= 0) {
                throw new IllegalArgumentException("Sem limite disponível para a compra");
            }
            CardHolderDTO cardHolderDTO = convertToResponseDTO(createTransaction.getCard().getCardHolder());
            TransactionDTO DTOTransaction = ModelMapper.convertTransacationToDTO(createTransaction);

            TransactionResponseDTO responseDTO = new TransactionResponseDTO();
            responseDTO.setCardHolderDTO(cardHolderDTO);
            responseDTO.setTransactionDTO(DTOTransaction);

            return responseDTO;
        } catch (Exception e) {
            System.err.println("Erro ao criar transação: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{idCardHolder}/invoices")
    @ResponseStatus(value = HttpStatus.OK)
    public ListTransactionDTO getTransactionToIdCardHolder(@PathVariable Long idCardHolder) {
        if (idCardHolder == null) {
            throw new CardNotFoundExceptions(idCardHolder);
        }

        try {
            List<Transaction> transactions = transactionService.getTransactionToIdCardHolder(idCardHolder);

            if (transactions == null || transactions.isEmpty()) {
                throw new EntityNotFoundException("Transações não encontradas para o CardHolder ID: " + idCardHolder);
            }

            CardHolder cardHolder = transactions.get(0).getCard().getCardHolder(); // Assume que cada transação tem um único cartão associado

            CardHolderTransactionDTO cardHolderDTO = new CardHolderTransactionDTO(
                    cardHolder.getName(),
                    cardHolder.getDocumentNumber(),
                    cardHolder.getBirthDate());

            Card card = transactions.get(0).getCard(); // Assumindo que cada transação está associada a um único cartão

            CardTransactionDTO cardDTO = new CardTransactionDTO(
                    card.getNumberCard(),
                    card.getCardExpiration(),
                    card.getCardCVV());

            List<TransactionDTOInvoice> transactionDTOList = new ArrayList<>();

            for (Transaction transaction : transactions) {
                TransactionDTOInvoice transactionDTOInvoice = new TransactionDTOInvoice(
                        transaction.getDescription(),
                        transaction.getPriceValue(),
                        transaction.getTransactionTime());
                transactionDTOList.add(transactionDTOInvoice);
            }

            return new ListTransactionDTO(
                    cardHolderDTO,
                    cardDTO,
                    transactionDTOList);
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(idCardHolder);
        }
    }



//    @GetMapping("/{idCardHolder}/invoices")
//    @ResponseStatus(value = HttpStatus.OK)
//    public ListTransactionDTO getTransactionToIdCardHolder(@PathVariable Long idCardHolder) {
//        if (idCardHolder == null) {
//            throw  new CardNotFoundExceptions(idCardHolder);
//        }
//        try {
//            ListTransactionDTO listTransactionDTO = transactionService.getTransactionToIdCardHolder(idCardHolder);
//
//            if (listTransactionDTO == null) {
//                throw new EntityNotFoundException("Transações não encontradas para o CardHolder ID: " + idCardHolder);
//            }
//
//            return listTransactionDTO;
//        } catch (EmptyResultDataAccessException e) {
//            throw new CardNotFoundExceptions(idCardHolder);
//        }
//    }

}

