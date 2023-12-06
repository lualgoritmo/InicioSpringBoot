package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.util.MapperConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.lucianobass.cardactivity.util.MapperConvert.convertDTOToCardHolder;
import static com.lucianobass.cardactivity.util.MapperConvert.convertToResponseDTO;

@RestController
@RequestMapping(value = "/cardholder")
public class CardHolderController {
    @Autowired
    private CardHolderService cardHolderService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CardHolderDTO createCardHolder(@RequestBody CardHolderDTO cardHolderDTO) {
        CardHolder createCardHolder = cardHolderService.createCard(convertDTOToCardHolder(cardHolderDTO));
        CardHolderDTO responseCardHolderDTO = convertToResponseDTO(createCardHolder);
        return responseCardHolderDTO;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CardHolderDTO> getAllCardHolder() {
        List<CardHolder> listCardHolder = cardHolderService.getAllCardsHolders();
        List<CardHolderDTO> responseListCardHolderDTO = listCardHolder.stream()
                .map(MapperConvert::convertToResponseDTO)
                .collect(Collectors.toList());
        return responseListCardHolderDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolderDTO findByIdCardHolder(@PathVariable Long id) {
        CardHolder cardHolderId = cardHolderService.getByIdCardHolder(id);
        return convertToResponseDTO(cardHolderId);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CardHolderDTO updateCardHolder(@PathVariable Long id,
                                          @RequestBody(required = false) CardHolder cardHolder) {
        CardHolder updateCardHolder = cardHolderService.updateCardHolder(id, cardHolder);
        return convertToResponseDTO(updateCardHolder);
    }

    @PutMapping("/{id}/activate")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolderDTO activateCardHolder(@PathVariable Long id) {
        try {
            CardHolder updatedCardHolder = cardHolderService.activateCard(id);

            if (updatedCardHolder != null) {
                return convertToResponseDTO(updatedCardHolder);
            } else {
                throw new IllegalStateException("O cartão já está ativo ou não há cartão associado");
            }
        } catch (CardNotFoundExceptions e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro durante a ativação do CardHolder: " + e.getMessage());
        }
    }



//    @PutMapping("/{id}/activate")
//    @ResponseStatus(code = HttpStatus.OK)
//    public CardHolderDTO activateCardHolder(@PathVariable Long id, @RequestBody Map<String, Object> request) {
//        //Boolean cardActive = (Boolean) request.get("cardActive");
//        CardHolder cardHolder = cardHolderService.activateCard(id);
//        return convertToResponseDTO(cardHolder);
//    }

//    @PutMapping("/{id}/activate")
//    @ResponseStatus(code = HttpStatus.OK)
//    public CardHolderDTO activateCardHolder(@PathVariable Long id) {
//        if (id == null) {
//            throw new IllegalArgumentException("O ID não pode ser nulo para a ativação");
//        }
//        try {
//            CardHolder cardHolder = cardHolderService.getByIdCardHolder(id)
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
//        CardHolder cardHolder = cardHolderService.activateCard(id);
//        return convertToResponseDTO(cardHolder);
//    }

    @PutMapping("/{id}/deactivate")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolder deactivateCardHolder(@PathVariable Long id) {
        return cardHolderService.deactivateCard(id);
    }

    @DeleteMapping(value = "/{id}/deletedcard")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteIdCardHolder(@PathVariable Long id) {
        cardHolderService.deleteIdCard(id);
    }
}
