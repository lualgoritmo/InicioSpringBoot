package com.lucianobass.cardactivity.controllerresources.controller;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.exceptions.CardNotFoundExceptions;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.services.CardHolderService;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.lucianobass.cardactivity.util.ModelMapper.*;

@RestController
@RequestMapping(value = "/cards")
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
                .map(ModelMapper::convertToResponseDTO)
                .collect(Collectors.toList());
        return responseListCardHolderDTO;
    }

    @GetMapping("/cardholder/{documentNumber}")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolderDTO getByCardHolderDocumentNumber(@PathVariable String documentNumber) {
        CardHolder cardHolder = cardHolderService.getByCardHolderDocumentNumber(documentNumber);
        return convertToResponseDTO(cardHolder);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CardHolderDTO updateCardHolder(@PathVariable Long id,
                                          @RequestBody(required = false) CardHolder updatedCardHolder) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para deletar");
        }

        try {

            CardHolder updatedResult = cardHolderService.updateCardHolder(id, updatedCardHolder);

            return convertToResponseDTO(updatedResult);
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(id);
        }
    }

    @PatchMapping("/{documentNumber}")
    @ResponseStatus(HttpStatus.OK)
    public CardHolderDTO updateCardStatusByDocumentNumber(
            @PathVariable String documentNumber,
            @RequestParam(defaultValue = "false") @RequestBody boolean activate) {
        CardHolder cardHolder = cardHolderService.updateCardStatusByDocumentNumber(documentNumber, activate);
        validateCardHolder(cardHolder);
        cardHolder.getCard().setCardActive(activate);
        return ModelMapper.convertToResponseDTO(cardHolder);
    }

    @DeleteMapping(value = "/{id}/deletedcard")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteIdCardHolder(@PathVariable Long id) {
        cardHolderService.deleteIdCard(id);
    }
}
