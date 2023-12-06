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
    public CardHolder getByIdCardHolder(@PathVariable Long id) {
        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );
        return cardHolder;
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

        BeanUtils.copyProperties(cardHolder, existingCardHolder, "id");
        return cardHolderRepository.save(existingCardHolder);
    }


    @Transactional
    public CardHolder activateCard(Long id) {
        return cardHolderRepository.findById(id)
                .map(cardHolder -> {
                    if (cardHolder.getCard() != null && Boolean.FALSE.equals(cardHolder.getCard().getCardActive())) {
                        cardHolder.getCard().setCardActive(true);
                        return cardHolderRepository.save(cardHolder);
                    } else if (cardHolder.getCard() != null && Boolean.TRUE.equals(cardHolder.getCard().getCardActive())) {
                        System.out.println("O cartão já está ativo para o CardHolder com ID: " + id);
                        return null;
                    } else {
                        System.out.println("Não possui um cartão associado para o CardHolder com ID: " + id);
                        return null;
                    }
                })
                .orElseThrow(() -> new CardNotFoundExceptions(id));
    }

    @Transactional
    public CardHolder deactivateCardHolder(Long id) {
        return cardHolderRepository.findById(id)
                .map(cardHolder -> {
                    if (cardHolder.getCard() != null && Boolean.TRUE.equals(cardHolder.getCard().getCardActive())) {
                        cardHolder.getCard().setCardActive(false);
                        return cardHolderRepository.save(cardHolder);
                    } else if (cardHolder.getCard() != null && Boolean.FALSE.equals(cardHolder.getCard().getCardActive())) {
                        System.out.println("O cartão já está desativado para o CardHolder com ID: " + id);
                        return null;
                    } else {
                        System.out.println("Não possui um cartão associado para o CardHolder com ID: " + id);
                        return null;
                    }
                })
                .orElseThrow(() -> new CardNotFoundExceptions(id));
    }

}
