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
    public InvoiceDTO createInvoice(@PathVariable Long idCardHolder, @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = ModelMapper.convertDTOTOInvoice(invoiceDTO);
        invoice = invoiceService.createInvoice(idCardHolder, invoice);
        return ModelMapper.convertInvoiceTODTO(invoice);
    }
}
