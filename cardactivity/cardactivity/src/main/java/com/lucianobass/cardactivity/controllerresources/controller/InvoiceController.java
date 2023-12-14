package com.lucianobass.cardactivity.controllerresources.controller;

import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.invoiceDTO.ListInvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardHolderTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.services.InvoiceService;
import com.lucianobass.cardactivity.services.TransactionService;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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

    @PostMapping("/{id}/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceDTO createInvoice(@PathVariable Long id) {
        Long invoiceId = invoiceService.createInvoice(id);

        if (invoiceId == null) {
            System.out.println("nulo!");
        }

        Invoice createdInvoice = invoiceService.getInvoiceById(invoiceId);
        return ModelMapper.convertInvoiceTODTO(createdInvoice);
    }

    @GetMapping("/{id}/invoices")
    @ResponseStatus(HttpStatus.OK)
    public ListInvoiceDTO getInvoiceDetails(
            @PathVariable Long id,
            @PathVariable Long invoiceId) {
        CardHolder cardHolder = new CardHolder();
        CardHolderTransactionDTO cardHolderInvoiceDTO = new CardHolderTransactionDTO(
                cardHolder.getName(),
                cardHolder.getDocumentNumber(),
                cardHolder.getBirthDate()
        );
        CardTransactionDTO cardInvoiceDTO = new CardTransactionDTO(
                cardHolder.getCard().getNumberCard(),
                cardHolder.getCard().getCardExpiration()
        );
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        List<ListInvoiceDTO> listInvoiceDTOList = new ArrayList<>();

        ListInvoiceDTO listResponseInvoiceDTO = new ListInvoiceDTO(
                cardHolderInvoiceDTO,
                cardInvoiceDTO,
                Collections.singletonList(ModelMapper.convertInvoiceTODTO(invoice))
        );

        return listResponseInvoiceDTO;
    }
}
