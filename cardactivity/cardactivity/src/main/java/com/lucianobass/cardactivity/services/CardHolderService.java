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
//    @Transactional()
//    public List<CardHolder> getAllCardsHolders() {
//        List<CardHolder> cardHolder = cardHolderRepository.findAll();
//        return cardHolder.stream()
//                .map(MapperConvert::convertToResponseDTO)
//                .collect(Collectors.toList());
//    }

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

//    @Transactional()
//    public CardHolderDTO updateCardHolder(Long id, @RequestBody CardHolderDTO cardHolderDTO) {
//        if (id == null) {
//            throw new IllegalArgumentException("O ID não pode ser nulo para a atualização");
//        }
//
//        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
//                () -> new CardNotFoundExceptions(id)
//        );
//
//        BeanUtils.copyProperties(convertDTOToCardHolder(cardHolderDTO), cardHolder, "id");
//        return convertToResponseDTO(cardHolderRepository.save(cardHolder));
//    }

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


//    @Transactional
//    public CardHolder activateCard(Long id) {
//        if (id == null) {
//            throw new IllegalArgumentException("O ID não pode ser nulo para a ativação");
//        }
//
//        try {
//            CardHolder cardHolder = cardHolderRepository.findById(id)
//                    .orElseThrow(() -> new CardNotFoundExceptions(id));
//
//            if (cardHolder.getCard() != null && Boolean.FALSE.equals(cardHolder.getCard().getCardActive())) {
//                cardHolder.getCard().setCardActive(true);
//                cardHolderRepository.save(cardHolder);
//                return cardHolder;
//            } else if (cardHolder.getCard() != null && Boolean.TRUE.equals(cardHolder.getCard().getCardActive())) {
//
//                System.out.println("O cartão já está ativo para o CardHolder com ID: " + id);
//                return null;
//            } else {
//
//                System.out.println("Não possui um cartão associado para o CardHolder com ID: " + id);
//                return null;
//            }
//        } catch (CardNotFoundExceptions e) {
//
//            System.out.println("CardHolder não encontrado: " + e.getMessage());
//
//            throw e;
//        } catch (Exception e) {
//
//            System.out.println("Erro durante a ativação do CardHolder: " + e.getMessage());
//
//            throw new RuntimeException("Erro durante a ativação do CardHolder", e);
//        }
//    }

    @Transactional
    public CardHolder deactivateCard(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para a desativação");
        }

        try {
            CardHolder cardHolder = cardHolderRepository.findById(id)
                    .orElseThrow(() -> new CardNotFoundExceptions(id));

            if (Boolean.TRUE.equals(cardHolder.getCard().getCardActive())) {

                cardHolder.getCard().setCardActive(false);

                cardHolderRepository.save(cardHolder);

                return cardHolder;
            } else {

                System.out.println("O cartão já está inativo para o CardHolder com ID: " + id);
                return null;
            }
        } catch (CardNotFoundExceptions e) {
            System.out.println("CardHolder não encontrado: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Erro durante a desativação do CardHolder: " + e.getMessage());
            throw new RuntimeException("Erro durante a desativação do CardHolder", e);
        }
    }

}
