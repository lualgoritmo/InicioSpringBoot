package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.controllerresources.dto.TransactionDTO;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    TransactionService transactionService;
    CardHolderService cardHolderService;

    @Autowired
    public TransactionController(TransactionService transactionService, CardHolderService cardHolderService) {
        this.transactionService = transactionService;
        this.cardHolderService = cardHolderService;
    }

    @PostMapping("/{id}/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TransactionDTO createTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        if (id == null || transactionDTO == null) {
            throw new IllegalArgumentException("ID ou TransactionDTO inválido");
        }

        try {
            TransactionDTO result = transactionService.createTransaction(id, transactionDTO);
            System.out.println("Transaction criada com sucesso: " + result);
            return result;
        } catch (Exception e) {
            System.err.println("Erro ao criar transação: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
