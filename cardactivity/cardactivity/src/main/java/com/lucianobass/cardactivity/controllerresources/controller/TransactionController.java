package com.lucianobass.cardactivity.controllerresources.controller;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.*;
import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.services.TransactionService;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.lucianobass.cardactivity.util.ModelMapper.convertCardHolderTODTO;
import static com.lucianobass.cardactivity.util.ModelMapper.convertCardToCardTransactionDTO;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private CardHolderService cardHolderService;
    @Autowired
    public TransactionController(TransactionService transactionService, CardHolderService cardHolderService) {
        this.transactionService = transactionService;
        this.cardHolderService = cardHolderService;
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
            createTransaction.getCard().getCardLimit();

            CardHolderDTO cardHolderDTO = convertCardHolderTODTO(createTransaction.getCard().getCardHolder());
            TransactionDTO DTOTransaction = ModelMapper.convertTransacationToDTO(createTransaction);

            TransactionResponseDTO responseDTO = new TransactionResponseDTO();
            responseDTO.setCardHolder(cardHolderDTO);
            responseDTO.setTransaction(DTOTransaction);

            return responseDTO;
        } catch (Exception e) {
            System.err.println("Erro ao criar transação: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{idCardHolder}/invoices")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ListTransactionDTO> getTransactionToIdCardHolder(@PathVariable Long idCardHolder) {
        try {
            if (idCardHolder == null) {
                throw new CardNotFoundExceptions(null);
            }

            // Obtenha o CardHolder do serviço ou repositório
            CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCardHolder);

            if (cardHolder == null) {
                throw new CardNotFoundExceptions(idCardHolder);
            }

            // Obtenha as transações para o CardHolder
            List<Transaction> transactions = transactionService.getTransactionToIdCardHolder(idCardHolder);

            if (transactions == null || transactions.isEmpty()) {
                throw new EntityNotFoundException("Transações não encontradas para o Cartão com número: " + idCardHolder);
            }

            // Crie os DTOs usando o ModelMapper
            CardHolderDTO cardHolderDTO = ModelMapper.convertCardHolderTODTO(cardHolder);

            // Ajuste aqui: Atribua a um CardDTO, já que ModelMapper.convertCardToDTO retorna um CardDTO
            CardTransactionDTO cardDTO = convertCardToCardTransactionDTO(transactions.get(0).getCard());

            List<ListTransactionDTOInvoice> invoiceDTOList = new ArrayList<>();

            for (Transaction transaction : transactions) {
                // Mapeie as transações para DTOs usando ModelMapper
                List<Transaction> listTransaction = ModelMapper.returnListTransactionDTO(Collections.singletonList(transaction));

                // Criar um novo DTO de fatura e adicionar à lista
                InvoiceDTO invoiceDTO = new InvoiceDTO();
                invoiceDTO.setTotal(transaction.getPriceValue());
                invoiceDTO.setStatus("IN_PROGRESS");
                invoiceDTO.setTransactions(listTransaction);

                // Use CardHolderDTO para ListTransactionDTOInvoice
                ListTransactionDTOInvoice listTransactionDTOInvoice = new ListTransactionDTOInvoice(cardHolderDTO, cardDTO, invoiceDTO);

                invoiceDTOList.add(listTransactionDTOInvoice);
            }

            // Objeto de resposta consolidado
            ListTransactionDTO listTransactionDTOResponse = new ListTransactionDTO(cardHolderDTO, cardDTO, invoiceDTOList);

            // Retorne a resposta
            return new ResponseEntity<>(listTransactionDTOResponse, HttpStatus.OK);

        } catch (CardNotFoundExceptions | EntityNotFoundException e) {
            // Tratar a exceção ou lançar novamente, se necessário
        }

        // Retornar uma resposta padrão em caso de erro
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // @GetMapping("/{idCardHolder}/invoices")
//    @ResponseStatus(value = HttpStatus.OK)
//    public ListTransactionDTO getTransactionToIdCardHolder(@PathVariable Long idCardHolder) {
//        if (idCardHolder == null) {
//            throw new CardNotFoundExceptions(null);
//        }
//
//        try {
//            List<Transaction> transactions = transactionService.getTransactionToIdCardHolder(idCardHolder);
//
//            if (transactions == null || transactions.isEmpty()) {
//                throw new EntityNotFoundException("Transações não encontradas para o CardHolder ID: " + idCardHolder);
//            }
//
//            CardHolder cardHolder = transactions.get(0).getCard().getCardHolder();
//
//            CardHolderTransactionDTO cardHolderDTO = new CardHolderTransactionDTO(
//                    cardHolder.getName(),
//                    cardHolder.getDocumentNumber(),
//                    cardHolder.getBirthDate());
//
//            Card card = transactions.get(0).getCard();
//
//
//            CardTransactionDTO cardDTO = new CardTransactionDTO(
//                    card.getNumberCard(),
//                    card.getCardExpiration());
//
//            List<TransactionDTO> transactionDTOList = new ArrayList<>();
//            InvoiceDTO invoiceDTO = new InvoiceDTO();
//            for (Transaction transaction : transactions) {
//                TransactionDTO transactionDTOInvoice = new TransactionDTO(
//                        transaction.getDescription(),
//                        transaction.getPriceValue());
//                transactionDTOList.add(transactionDTOInvoice);
//            }
//
//            return new ListTransactionDTO(
//                    cardHolderDTO,
//                    cardDTO,
//                    transactionDTOList);
//        } catch (EmptyResultDataAccessException e) {
//            throw new CardNotFoundExceptions(idCardHolder);
//        }
//    }


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