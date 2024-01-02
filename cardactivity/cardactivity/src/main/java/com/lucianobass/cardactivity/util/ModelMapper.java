package com.lucianobass.cardactivity.util;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.invoiceDTO.ListInvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.CardTransactionDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

    public static CardHolderDTO convertCardHolderTODTO(CardHolder cardHolder) {
        CardHolderDTO cardHolderResponseDTO = new CardHolderDTO(
                cardHolder.getName(),
                cardHolder.getDocumentNumber(),
                cardHolder.getBirthDate()
        );

        if (cardHolder.getCard() != null) {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setNumberCard(cardHolder.getCard().getNumberCard());
            cardDTO.setCardExpiration(cardHolder.getCard().getCardExpiration());
            cardDTO.setCardLimit(cardHolder.getCard().getCardLimit());
            cardDTO.setCardCVV(cardHolder.getCard().getCardCVV());
            cardDTO.setAvailableLimit(cardHolder.getCard().getAvailableLimit());


            cardDTO.setCardActive(cardHolder.getCard().getCardActive());

            cardHolderResponseDTO.setCard(cardDTO);
        }

        return cardHolderResponseDTO;
    }

    public static CardHolder convertDTOToCardHolder(CardHolderDTO cardHolderDTO) {
        CardHolder cardHolder = new CardHolder(
                cardHolderDTO.getName(),
                cardHolderDTO.getDocumentNumber(),
                cardHolderDTO.getBirthDate());

        return cardHolder;
    }

    public static void validateCardHolder(CardHolder cardHolder) {

        if (cardHolder == null || cardHolder.getName().isEmpty() || cardHolder.getDocumentNumber().isEmpty() ||
                cardHolder.getBirthDate().isEmpty()) {
            throw new IllegalArgumentException("O usuário não existe!");
        }
    }


    public static Transaction convertDTOToTransacation(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            throw new IllegalArgumentException("DTO de transação não pode ser nulo");
        }
        return new Transaction(
                transactionDTO.getDescription(),
                transactionDTO.getPriceValue()
        );
    }
    public static Invoice convertInvoiceTODTO(InvoiceDTO invoiceDTO) {
        List<Transaction> transaction = new ArrayList<>();
        Card card = new Card();

        return new Invoice(
                invoiceDTO.getTotal(),
                invoiceDTO.getStatus(),
                returnListTransaction(transaction),
                LocalDate.now(),
                LocalDate.now(),
                card
        );
    }

    public static List<Invoice> convertToInvoiceList(ListInvoiceDTO listInvoiceDTO) {
        return listInvoiceDTO.getInvoices().stream()
                .map(ModelMapper::convertInvoiceTODTO)
                .collect(Collectors.toList());
    }
    public static List<Transaction> returnListTransaction(List<Transaction> transactions) {
        if (transactions == null) {
            return Collections.emptyList();
        }

        return transactions.stream()
                .map(transaction -> new Transaction(
                        transaction.getDescription(),
                        transaction.getPriceValue()))
                .collect(Collectors.toList());
    }

    public static TransactionDTO convertTransacationToDTO(Transaction transaction) {
        //transactionDTO.setTransactionTime(transaction.getTransactionTime(LocalDateTime.now()));

        return new TransactionDTO(
                transaction.getDescription(),
                transaction.getPriceValue()
        );
    }

//    public static List<InvoiceDTO> convertInvoiceTODTO(List<Invoice> invoices) {
//        return invoices.stream()
//                .map(invoice -> new InvoiceDTO(
//                   invoice.getTotal(),
//                   invoice.getStatus(),
//                   invoice.getTransactions()
//                ))
//                .collect(Collectors.toList());
//    }
    public static List<TransactionDTO> convertListTransactionTODTO(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getDescription(),
                        transaction.getPriceValue()
                ))
                .collect(Collectors.toList());
    }
    public static CardDTO convertCardToDTO(Card card) {
        return new CardDTO(
                card.getNumberCard(),
                card.getCardExpiration(),
                card.getAvailableLimit(),
                card.getCardLimit(),
                card.getCardCVV(),
                card.getCardActivate()
        );
    }

    public static CardTransactionDTO convertCardToCardTransactionDTO(Card card) {
        if (card == null) {
            return null;
        }

        return new CardTransactionDTO(
                card.getNumberCard(),
                card.getCardExpiration()
        );
    }

//    public static List<TransactionDTO> convertTransactionsToDTO(List<Transaction> transactions) {
//        return transactions.stream()
//                .map(transaction -> new TransactionDTO(
//                        transaction.getDescription(),
//                        transaction.getPriceValue(),
//                        transaction.getTransactionTime()
//                ))
//                .collect(Collectors.toList());
//    }

//    public static List<TransactionDTO> convertTransactionsToDTO(List<Transaction> transactions) {
//        return transactions.stream()
//                .map(transaction -> new TransactionDTO(
//                        transaction.getDescription(),
//                        transaction.getPriceValue(),
//                        transaction.getTransactionTime()
//                ))
//                .collect(Collectors.toList());
//    }
//    public static List<TransactionDTO> convertTransactionsToDTO(List<Transaction> transactions) {
//        return transactions.stream()
//                .map(transaction -> new TransactionDTO(
//                        transaction.getDescription(),
//                        transaction.getPriceValue(),
//                        transaction.getTransactionTime()  // Adicionei o tempo da transação
//                ))
//                .collect(Collectors.toList());
//    }
//    public static List<TransactionDTO> convertTransactionsToDTO(List<Transaction> transactions) {
//        return transactions.stream()
//                .map(transaction -> new TransactionDTO(
//                        transaction.getDescription(),
//                        transaction.getPriceValue()
//                ))
//                .collect(Collectors.toList());
//    }

//    public static Invoice convertDTOTOInvoice(InvoiceDTO invoiceDTO) {
//        List<Transaction> transactions = invoiceDTO.getTransactions().stream()
//                .map(transactionDTO -> new Transaction(transactionDTO.getDescription(), transactionDTO.getPriceValue()))
//                .collect(Collectors.toList());
//
//        return new Invoice(
//                invoiceDTO.getTotal(),
//                invoiceDTO.getStatus(),
//                transactions
//        );
//    }

}