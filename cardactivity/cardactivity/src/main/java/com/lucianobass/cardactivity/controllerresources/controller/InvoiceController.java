package com.lucianobass.cardactivity.controllerresources.controller;

import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.services.InvoiceService;
import com.lucianobass.cardactivity.services.TransactionService;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/cards")
public class InvoiceController {

    private CardHolderService cardHolderService;
    private InvoiceService invoiceService;
    private TransactionService transactionService;

    @Autowired
    public InvoiceController(
            CardHolderService cardHolderService,
            InvoiceService invoiceService,
            TransactionService transactionService) {
        this.cardHolderService = cardHolderService;
        this.invoiceService = invoiceService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{cardId}/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceDTO> getInvoicesWithDetailsByCardId(@PathVariable Long cardId) {
        try {
            List<Invoice> invoices = invoiceService.getInvoicesWithDetailsByCardId(cardId);

            List<InvoiceDTO> invoiceDTOs = ModelMapper.convertInvoiceTODTO(invoices);

            return invoiceDTOs;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("CardHolder não encontrado para o número de documento: " + cardId);
        }
    }

//
//    @GetMapping("/{idCard}/invoices")
//    @ResponseStatus(HttpStatus.OK)
//    public ListInvoiceDTO getInvoices(@PathVariable Long idCard) {
//        CardHolder cardHolder = cardHolderService.getByIdCardHolder(idCard);
//        Card card = cardHolder.getCard();
//        List<Invoice> invoices = invoiceService.getInvoicesByCardId(idCard);
//
//        if (invoices == null || invoices.isEmpty()) {
//             new IllegalArgumentException("Não acho id, controller");
//        }
//
//        // Aqui você pode escolher retornar a última fatura, ou uma lista delas, dependendo dos requisitos
//        Invoice latestInvoice = invoices.get(invoices.size() - 1);
//
//        CardHolderTransactionDTO cardHolderDTO = new CardHolderTransactionDTO(
//                cardHolder.getName(),
//                cardHolder.getDocumentNumber(),
//                cardHolder.getBirthDate()
//        );
//
//        CardTransactionDTO cardDTO = new CardTransactionDTO(
//                card.getNumberCard(),
//                card.getCardExpiration()
//        );
//
//        // Aqui você precisa converter a entidade Invoice para o formato desejado no JSON
//        InvoiceDTO invoiceDTO = ModelMapper.convertInvoiceTODTO(latestInvoice);
//
//        // Monta a resposta final
//        ListInvoiceDTO listInvoiceDTO = new ListInvoiceDTO(
//                cardHolderDTO,
//                cardDTO,
//                Collections.singletonList(invoiceDTO)
//        );
//
//        return listInvoiceDTO;
//    }
}