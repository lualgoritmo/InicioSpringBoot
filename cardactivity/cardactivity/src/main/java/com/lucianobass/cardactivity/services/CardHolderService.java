package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;

import static com.lucianobass.cardactivity.util.ModelMapper.validateCardHolder;

@Service
public class CardHolderService {

    private final CardHolderRepository cardHolderRepository;
    @Autowired
    public CardHolderService(CardHolderRepository cardHolderRepository) {
        this.cardHolderRepository = cardHolderRepository;
    }

    @Transactional
    public CardHolder createCardHolder(CardHolder cardHolder) {
        //validateCardHolder(cardHolder);
        try {
            System.out.println("Card holder criado: " + cardHolder);
            return cardHolderRepository.save(cardHolder);
        } catch (Exception ex) {
            System.out.println("ERRO AO CRIAR");
            throw ex;
        }
    }

    @Transactional()
    public List<CardHolder> getAllCardsHolders() {
        return cardHolderRepository.findAll();
    }

    @Transactional()
    public CardHolder getByIdCardHolder(Long id) { //Aqui não vai o "@PathVariable", pois essa é uma anotação para o controller
        return cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );
        // o return poderia ser diretametne da linha de cima, pois fica "redundante" criar uma variável nesse caso
    }

    @Transactional
    public CardHolder getByCardHolderDocumentNumber(String documentNumber) { //Aqui não vai o "@PathVariable", pois essa é uma anotação para o controller
        CardHolder cardHolder = cardHolderRepository.findByDocumentNumber(documentNumber);
        if (cardHolder != null) {
            return cardHolder;
        } else {
            throw new IllegalArgumentException(); //Se cardHolder não existir, vc deve retornar a exception "CardNotFoundExceptions" não?
        }
    }

    @Transactional()
    public void deleteCardHolder(Long id) {  //Aqui não vai o "@PathVariable", pois essa é uma anotação para o controller
        try {
            System.out.println("TESTANDO AQUI ID: " + id);

            cardHolderRepository.deleteById(id);
            System.out.println("Passei aqui ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(id);
        }
    }

    @Transactional
    public CardHolder updateCardHolder(Long id, @RequestBody CardHolder cardHolder) {  //Aqui não vai o "@RequestBody", pois essa é uma anotação para o controller
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para a atualização");
        }

        CardHolder existingCardHolder = cardHolderRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundExceptions(id));

        // BeanUtils.copyProperties(cardHolder, existingCardHolder, "id");
        existingCardHolder.setName(cardHolder.getName());
        return cardHolderRepository.save(existingCardHolder);
    }

    public CardHolder updateCardStatusByDocumentNumber(String documentNumber, boolean active) {
        // Se necessário, implemente a lógica para buscar o CardHolder no banco de dados
        // Ainda é possível manter a validação, dependendo das suas necessidades
        CardHolder cardHolder = cardHolderRepository.findByDocumentNumber(documentNumber);
        if (cardHolder == null) {
            throw new EntityNotFoundException("CardHolder não encontrado para o número de documento: " + documentNumber);
        }
        if (cardHolder.getCard() != null) {
            cardHolder.getCard().setCardActive(active);
            return cardHolderRepository.save(cardHolder);
        } else {
            throw new IllegalStateException("Nenhum Card associado para esse cardholder");
        }
    }

//    @Transactional
//    public CardHolder updateCardStatusByDocumentNumber(String documentNumber, boolean activate) {
//        CardHolder cardHolder = cardHolderRepository.findByDocumentNumber(documentNumber);
//
//        if (cardHolder == null) {
//            throw new IllegalStateException("Número de documento não reconhecido!"); //ao invés de retornar um IllegalStateException, vc deve retornar um 404 (CardHolderNotFound)
//        }
//
//        if (cardHolder.getCard() != null) {
//            cardHolder.getCard().setCardActive(activate);
//            return cardHolderRepository.save(cardHolder);
//        } else {
//            throw new IllegalStateException("Nenhum Card associado para esse cardholder");
//        }
//    }

//    @Transactional
//    public void updateLimitCard(Long idCardHolder, Card card) {
//        CardHolder cardHolder = getByIdCardHolder(idCardHolder);
//
//        if (cardHolder.getCard() == null || !cardHolder.getCard().getCardActive()) {
//            throw new IllegalArgumentException("Ative o seu cartão para realizar compras");
//        }
//
//        Card existingCard = cardHolder.getCard();
//        existingCard.setCardLimit(card.getCardLimit());
//
//        // Certifique-se de salvar a alteração no objeto CardHolder, pois você atualizou a instância de Card
//        cardHolder.setCard(existingCard);
//
//        cardHolderRepository.save(cardHolder);
//    }


    @Transactional
    public void updateLimitCard(Long idCardHolder, Card card) {
        CardHolder cardHolder = getByIdCardHolder(idCardHolder);

        if (!cardHolder.getCard().getCardActive()) {
            throw new IllegalArgumentException("Ative o seu cartão para realizar compras");
        }

        Card existingCard = cardHolder.getCard();
        existingCard.setCardLimit(card.getCardLimit());

        cardHolderRepository.save(cardHolder);
    }
}