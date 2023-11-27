package com.lucianobass.cardactivity.controllerresources;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.services.CardHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cardholder")
public class CardHolderController {
    @Autowired
    private CardHolderService cardHolderService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CardHolder createCardHolder(@RequestBody CardHolderDTO cardHolderDTO) {
        return cardHolderService.createCard(cardHolderDTO);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CardHolder> getAllCardHolder() {
        return cardHolderService.getAllCardsHolders();
    }
}
