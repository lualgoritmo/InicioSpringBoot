package com.lucianobass.cardactivity.util;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.invoiceDTO.InvoiceDTO;
import com.lucianobass.cardactivity.controllerresources.transactionDTO.TransactionDTO;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.models.Invoice;
import com.lucianobass.cardactivity.models.Transaction;

public class ModelMapper {

    public static CardHolderDTO convertCardHolderTODTO(CardHolder cardHolder) {
        CardHolderDTO cardHolderResponseDTO = new CardHolderDTO(
                cardHolder.getName(),
                cardHolder.getDocumentNumber(),
                cardHolder.getBirthDate()
        );

        if (cardHolder.getCard() != null) {
            CardDTO cardDTO = new CardDTO(); //ao invés de usar os set`s do card .. você poderia usar o construtor
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

        if (cardHolderDTO == null) {
            System.out.println("DTO nulo");
        }
        return cardHolder;
    }

    public static void validateCardHolder(CardHolder cardHolder) {
        if (cardHolder.getName().isEmpty() || cardHolder.getDocumentNumber().isEmpty() ||
                cardHolder.getBirthDate().isEmpty()) {
            throw new IllegalArgumentException(" O usuário não existe! "); //aqui, a expetion poderia ser "usuário inválido, verifique os dados informados!"
        }
    }

    public static Transaction convertDTOToTransacation(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            throw new IllegalArgumentException("DTO de transação não pode ser nulo");
        }
        Transaction transaction = new Transaction(
                transactionDTO.getDescription(),
                transactionDTO.getPriceValue()
        );
        return transaction;
    }

    public static TransactionDTO convertTransacationToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO(
                transaction.getDescription(),
                transaction.getPriceValue()
        );
        //transactionDTO.setTransactionTime(transaction.getTransactionTime(LocalDateTime.now()));

        return transactionDTO;
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

    public static InvoiceDTO convertInvoiceTODTO(Invoice invoice) {
        return new InvoiceDTO(
                invoice.getTotal(),
                invoice.getStatus(),
                invoice.getTransactions()
        );
    }

    public static Invoice convertDTOTOInvoice(InvoiceDTO invoiceDTO) {
        return new Invoice(
                invoiceDTO.getTotal(),
                invoiceDTO.getStatus(),
                invoiceDTO.getTransactions());
    }
}
