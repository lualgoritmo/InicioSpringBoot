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

    @Autowired
    private CardHolderRepository cardHolderRepository;

    @Transactional
    public CardHolderDTO createCard(CardHolderDTO cardHolderDTO) {
        validateCardHolder(cardHolderDTO);
        try {
            CardHolder savedCardHolderDTO = cardHolderRepository.save(setCardHolder(cardHolderDTO));
            CardHolderDTO responseDTO = convertToResponseDTO(savedCardHolderDTO);
            return responseDTO;
        } catch (Exception ex) {
            System.out.println("ERRO AO CRIAR");
            throw ex;
        }
    }

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
    public CardHolder getByIdCardHolder(@PathVariable Long id) {
        return cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );
    }
    private CardHolderDTO convertToResponseDTO(CardHolder cardHolder) {
        CardHolderDTO responseDTO = new CardHolderDTO();
        //responseDTO.setId(cardHolder.getId());
        responseDTO.setName(cardHolder.getName());
        responseDTO.setDocumentNumber(cardHolder.getDocumentNumber());
        responseDTO.setBirthDate(cardHolder.getBirthDate());
        if (cardHolder.getCard() != null) {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setNumberCard(cardHolder.getCard().getNumberCard());
            cardDTO.setCardExpiration(cardHolder.getCard().getCardExpiration());
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
        cardHolder.setBirthDate(cardHolderDTO.getBirthDate());
        return cardHolder;
    }

    private void validateCardHolder(CardHolderDTO cardHolderDTO) {
        if (cardHolderDTO.getName().isEmpty()) {
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

    public CardHolderDTO updateCardHolder(Long id, @RequestBody CardHolderDTO cardHolderDTO) {
        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
                () -> new CardNotFoundExceptions(id)
        );

        BeanUtils.copyProperties(setCardHolder(cardHolderDTO), cardHolder, "id");
        return convertToResponseDTO(cardHolderRepository.save(cardHolder));
    }
}
