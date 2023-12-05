package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.services.CardHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cardholder")
public class CardHolderController {
    @Autowired
    private CardHolderService cardHolderService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CardHolderDTO createCardHolder(@RequestBody CardHolderDTO cardHolderDTO) {
        return cardHolderService.createCard(cardHolderDTO);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CardHolderDTO> getAllCardHolder() {
        return cardHolderService.getAllCardsHolders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolderDTO findByIdCardHolder(@PathVariable Long id) {
        return cardHolderService.getByIdCardHolder(id);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CardHolderDTO updateCardHolder(@PathVariable Long id,
                                          @RequestBody(required = false) CardHolderDTO cardHolderDTO) {
        return cardHolderService.updateCardHolder(id, cardHolderDTO);
    }
    @PutMapping("/{id}/activate")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolderDTO activateCardHolder(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        //Boolean cardActive = (Boolean) request.get("cardActive");
        return cardHolderService.activateCard(id);
    }

    @PutMapping("/{id}/deactivate")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolderDTO deactivateCardHolder(@PathVariable Long id) {
        return cardHolderService.deactivateCard(id);
    }

    @DeleteMapping(value = "/{id}/deletedcard")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteIdCardHolder(@PathVariable Long id) {
        cardHolderService.deleteIdCard(id);
    }
}
