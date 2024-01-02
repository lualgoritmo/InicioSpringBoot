package com.lucianobass.cardactivity.controllerresources.controller;

import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.invoiceDTO.ListInvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardHolderTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.services.InvoiceService;
import com.lucianobass.cardactivity.services.TransactionService;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cards")
public class InvoiceController {

    private final CardHolderService cardHolderService;
    private final
    InvoiceService invoiceService;

    @Autowired
    public InvoiceController(
            CardHolderService cardHolderService,
            InvoiceService invoiceService,
            TransactionService transactionServiceInvoice) {
        this.cardHolderService = cardHolderService;
        this.invoiceService = invoiceService;
    }

    //    @GetMapping("/{cardId}/invoices")
//    @ResponseStatus(HttpStatus.OK)
//    public List<InvoiceDTO> getInvoicesWithDetailsByCardId(@PathVariable Long cardId) {
//        try {
//            List<Invoice> invoices = invoiceService.getInvoicesWithDetailsByCardId(cardId);
//
//            List<InvoiceDTO> invoiceDTOs = ModelMapper.convertInvoiceTODTO(invoices);
//
//            return invoiceDTOs;
//        } catch (EntityNotFoundException e) {
//            throw new EntityNotFoundException("CardHolder não encontrado para o número de documento: " + cardId);
//        }
//    }
    @GetMapping("/{cardId}/invoices")
    @ResponseStatus(HttpStatus.OK)
    public ListInvoiceDTO getInvoices(@PathVariable Long cardId) {
        try {
            List<Invoice> invoices = invoiceService.getInvoicesWithDetailsByCardId(cardId);

            CardHolder cardHolder = cardHolderService.getByIdCardHolder(cardId);
            Card card = cardHolder.getCard();

            CardHolderTransactionDTO cardHolderDTO = new CardHolderTransactionDTO(
                    cardHolder.getName(),
                    cardHolder.getDocumentNumber(),
                    cardHolder.getBirthDate()
            );

            CardTransactionDTO cardDTO = ModelMapper.convertCardToCardTransactionDTO(card);

            List<InvoiceDTO> invoiceDTOList = invoices.stream()
                    .map(invoice -> {
                        InvoiceDTO dto = new InvoiceDTO();
                        dto.setStatus(invoice.getStatus());

                        List<TransactionDTO> transactionDTOList = invoice.getTransactions().stream()
                                .map(transaction -> new TransactionDTO(
                                        transaction.getDescription(),
                                        transaction.getPriceValue()))
                                .collect(Collectors.toList());

                        dto.setTransactions(transactionDTOList);

                        Float total = transactionDTOList.stream()
                                .map(TransactionDTO::getPriceValue)
                                .reduce(0f, Float::sum);
                        dto.setTotal(total);

                        return dto;
                    }).collect(Collectors.toList());

            Float total = invoiceDTOList.stream()
                    .map(InvoiceDTO::getTotal)
                    .reduce(0f, Float::sum);

            InvoiceDTO consolidatedInvoice = new InvoiceDTO();
            consolidatedInvoice.setStatus("IN_PROGRESS");
            consolidatedInvoice.setTotal(total);


            List<TransactionDTO> consolidatedTransactions = invoiceDTOList.stream()
                    .flatMap(invoiceDTO -> invoiceDTO.getTransactions().stream())
                    .collect(Collectors.toList());

            consolidatedInvoice.setTransactions(consolidatedTransactions);

            return new ListInvoiceDTO(cardHolderDTO, cardDTO,
                    Collections.singletonList(consolidatedInvoice));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }


//    @GetMapping("/{cardId}/invoices")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<ListInvoiceDTO> getInvoices(@PathVariable Long cardId) {
//        try {
//            List<Invoice> invoices = invoiceService.getInvoicesWithDetailsByCardId(cardId);
//
//            if (invoices.isEmpty()) {
//                throw new EntityNotFoundException("Não há faturas para o cartão com ID: " + cardId);
//            }
//
//            CardHolder cardHolder = serviceCardHolderInvoice.getByIdCardHolder(cardId);
//            Card card = cardHolder.getCard();
//
//            CardHolderTransactionDTO cardHolderDTO = new CardHolderTransactionDTO(
//                    cardHolder.getName(),
//                    cardHolder.getDocumentNumber(),
//                    cardHolder.getBirthDate()
//            );
//
//            CardTransactionDTO cardDTO = new CardTransactionDTO(
//                    card.getNumberCard(),
//                    card.getCardExpiration()
//            );
//
//            List<InvoiceDTO> invoiceDTOList = invoices.stream()
//                    .map(invoice -> {
//                        InvoiceDTO dto = new InvoiceDTO();
//                        dto.setStatus(invoice.getStatus());
//
//                        List<TransactionDTO> transactionDTOList = invoice.getTransactions().stream()
//                                .map(transaction -> new TransactionDTO(
//                                        transaction.getDescription(),
//                                        transaction.getPriceValue()))
//                                .collect(Collectors.toList());
//
//                        dto.setTransactions(transactionDTOList);
//
//                        // Calcular o valor total como Float
//                        Float total = transactionDTOList.stream()
//                                .map(TransactionDTO::getPriceValue)
//                                .reduce(0f, Float::sum);
//                        dto.setTotal(total);
//
//                        return dto;
//                    })
//                    .collect(Collectors.toList());
//
//            // Calcular o valor total da soma de todas as transações
//            Float total = invoiceDTOList.stream()
//                    .map(InvoiceDTO::getTotal)
//                    .reduce(0f, Float::sum);
//            InvoiceDTO invoiceDTO = new InvoiceDTO();
//            invoiceDTO.setTotal(total);
//
//            ListInvoiceDTO listInvoiceDTO = new ListInvoiceDTO(cardHolderDTO, cardDTO, invoiceDTOList);
//
//            return new ResponseEntity<>(listInvoiceDTO, HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

}
