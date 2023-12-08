package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

import static com.lucianobass.cardactivity.util.MapperConvert.validateCardHolder;

@Service
public class CardHolderService {

    private CardHolderRepository cardHolderRepository;

    @Autowired
    public void setCardHolderRepository(CardHolderRepository cardHolderRepository) {
        this.cardHolderRepository = cardHolderRepository;
    }

    @Transactional
    public CardHolder createCard(CardHolder cardHolder) {
        validateCardHolder(cardHolder);
        try {
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
    public CardHolder getByIdCardHolder(@PathVariable long id) {
        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );
        return cardHolder;
    }

    @Transactional
    public CardHolder getByCardHolderDocumentNumber(@PathVariable String documentNumber) {
        CardHolder cardHolder = cardHolderRepository.findByDocumentNumber(documentNumber);
        if (cardHolder == null) {
            throw new IllegalArgumentException();
        } else {
            return cardHolder;
        }
    }

    @Transactional()
    public void deleteIdCard(@PathVariable Long id) {
        try {
            cardHolderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(id);
        }
    }

    @Transactional
    public CardHolder updateCardHolder(Long id, @RequestBody CardHolder cardHolder) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para a atualização");
        }

        CardHolder existingCardHolder = cardHolderRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundExceptions(id));

       // BeanUtils.copyProperties(cardHolder, existingCardHolder, "id");
        existingCardHolder.setName(cardHolder.getName());
        return cardHolderRepository.save(existingCardHolder);
    }


    @Transactional
    public CardHolder updateCardStatusByDocumentNumber(String documentNumber, boolean activate) {
        CardHolder cardHolder = cardHolderRepository.findByDocumentNumber(documentNumber);

        if (cardHolder == null) {
            throw new IllegalStateException("Número de documento não reconhecido!");
        }

        if (cardHolder.getCard() != null) {
            cardHolder.getCard().setCardActive(activate);
            return cardHolderRepository.save(cardHolder);
        } else {
            throw new IllegalStateException("Nenhum Card associado para esse cardholder");
        }
    }

}
