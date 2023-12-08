package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.dto.ListTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.dto.TransactionDTO;
import com.lucianobass.cardactivity.controllerresources.dto.TransactionResponseDTO;
import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.Transaction;
import com.lucianobass.cardactivity.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.lucianobass.cardactivity.util.MapperConvert.convertToResponseDTO;
import static com.lucianobass.cardactivity.util.MapperConvert.convertTransacationToDTO;

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
        List<Transaction> listTransaction = new ArrayList<>();
        if (cardholderId == null || transactionDTO == null) {
            throw new IllegalArgumentException("ID do cardholder ou Transação inválido");
        }

        try {
            Transaction createTransaction = transactionService.createTransaction(cardholderId, transactionDTO);
            System.out.println("Transação criada com sucesso.");
            CardHolderDTO cardHolderDTO = convertToResponseDTO(createTransaction.getCard().getCardHolder());
            //CardDTO cardDTO = convertCardToDTO(createTransaction.getCard());
            TransactionDTO DTOTransaction = convertTransacationToDTO(createTransaction);

            TransactionResponseDTO responseDTO = new TransactionResponseDTO();
            responseDTO.setCardHolderDTO(cardHolderDTO);
            //responseDTO.setCardDTO(cardDTO);
            responseDTO.setTransactionDTO(DTOTransaction);

            return responseDTO;
        } catch (Exception e) {
            System.err.println("Erro ao criar transação: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{idCardHolder}")
    @ResponseStatus(value = HttpStatus.OK)
    public ListTransactionDTO getTransactionToIdCardHolder(@PathVariable Long idCardHolder) {
        try {
            // Chama o serviço para obter a ListTransactionDTO
            ListTransactionDTO listTransactionDTO = transactionService.getTransactionToIdCardHolder(idCardHolder);

            // Certifique-se de que listTransactionDTO não seja nulo antes de retornar
            if (listTransactionDTO == null) {
                // Lidar com o caso em que não foram encontradas transações para o cardHolder
                throw new EntityNotFoundException("Transações não encontradas para o CardHolder ID: " + idCardHolder);
            }

            return listTransactionDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(idCardHolder);
        }
    }

}

