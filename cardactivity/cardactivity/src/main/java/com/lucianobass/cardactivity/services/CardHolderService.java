package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.controllerresources.dto.CardDTO;
import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
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
import java.util.stream.Collectors;

@Service
public class CardHolderService {

    //    @Autowired
//    private CardHolderRepository cardHolderRepository;

    private CardHolderRepository cardHolderRepository;

    @Autowired
    public void setCardHolderRepository(CardHolderRepository cardHolderRepository) {
        this.cardHolderRepository = cardHolderRepository;
    }

    @Transactional
    public CardHolderDTO createCard(CardHolderDTO cardHolderDTO) {
        CardHolder cardHolder = setCardHolder(cardHolderDTO);
        CardHolder savedCardHolder = cardHolderRepository.save(cardHolder);
        return convertToResponseDTO(savedCardHolder);
    }

//    @Transactional
//    public CardHolderDTO createCard(CardHolderDTO cardHolderDTO) {
//        validateCardHolder(cardHolderDTO);
//        try {
//            CardHolder savedCardHolderDTO = cardHolderRepository.save(setCardHolder(cardHolderDTO));
//            CardHolderDTO responseDTO = convertToResponseDTO(savedCardHolderDTO);
//            return responseDTO;
//        } catch (Exception ex) {
//            System.out.println("ERRO AO CRIAR");
//            throw ex;
//        }
//    }

//        @Transactional()
//    public List<CardHolderDTO> getAllCardsHolders() {
//        return cardHolderRepository.findAll();
//    }

    @Transactional()
    public List<CardHolderDTO> getAllCardsHolders() {
        List<CardHolder> cardHolder = cardHolderRepository.findAll();
        return cardHolder.stream().map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    public CardHolderDTO getByIdCardHolder(@PathVariable Long id) {
        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );
        return convertToResponseDTO(cardHolder);
    }

    CardHolderDTO convertToResponseDTO(CardHolder cardHolder) {
        CardHolderDTO responseDTO = new CardHolderDTO();
        responseDTO.setName(cardHolder.getName());
        responseDTO.setDocumentNumber(cardHolder.getDocumentNumber());
        responseDTO.setBirthDate(cardHolder.getBirthDate());
        //responseDTO.setId(cardHolder.getId());
        if (cardHolder.getCard() != null) {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setNumberCard(cardHolder.getCard().getNumberCard());
            cardDTO.setCardExpiration(cardHolder.getCard().getCardExpiration());
            cardDTO.setCardLimit(cardHolder.getCard().getCardLimit());
            cardDTO.setCardCVV(cardHolder.getCard().getCardCVV());
            cardDTO.setAvailableLimit(cardHolder.getCard().getAvailableLimit());
            responseDTO.setCard(cardDTO);
        }
        return responseDTO;
    }

//    private CardHolderDTO convertToResponseDTO(CardHolder cardHolder) {
//        CardHolderDTO responseDTO = new CardHolderDTO();
//        responseDTO.setName(cardHolder.getName());
//        responseDTO.setDocumentNumber(cardHolder.getDocumentNumber());
//        responseDTO.setBirthDate(cardHolder.getBirthDate());
//        return responseDTO;
//    }

    private CardHolder setCardHolder(CardHolderDTO cardHolderDTO) {
        CardHolder cardHolder = new CardHolder();
        cardHolder.setName(cardHolderDTO.getName());
        cardHolder.setDocumentNumber(cardHolderDTO.getDocumentNumber());
        if (cardHolderDTO.getName() != null) {
            cardHolder.setBirthDate(cardHolderDTO.getBirthDate());
        } else {
            System.out.println("DATA CHEGANDO NULL");
        }
        return cardHolder;
    }

    private void validateCardHolder(CardHolderDTO cardHolderDTO) {
        if (cardHolderDTO.getName().isEmpty() || cardHolderDTO.getDocumentNumber().isEmpty() ||
                cardHolderDTO.getBirthDate().isEmpty()) {
            throw new IllegalArgumentException(" O usuário não existe! ");
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
//
//    public CardHolderDTO updateCardHolder(Long id, @RequestBody CardHolderDTO cardHolderDTO) {
//        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
//                () -> new CardNotFoundExceptions(id)
//        );
//
//        BeanUtils.copyProperties(setCardHolder(cardHolderDTO), cardHolder, "id");
//        return convertToResponseDTO(cardHolderRepository.save(cardHolder));
//    }

    public CardHolderDTO updateCardHolder(Long id, @RequestBody CardHolderDTO cardHolderDTO) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para a atualização");
        }

        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );

        BeanUtils.copyProperties(setCardHolder(cardHolderDTO), cardHolder, "id");
        return convertToResponseDTO(cardHolderRepository.save(cardHolder));
    }

}
