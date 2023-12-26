package com.lucianobass.cardactivity.controllerresources.controller;

import com.lucianobass.cardactivity.controllerresources.dto.CardHolderDTO;
import com.lucianobass.cardactivity.controllerresources.dto.UpdateCardStatusRequestDTO;
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

import static com.lucianobass.cardactivity.util.ModelMapper.convertCardHolderTODTO;
import static com.lucianobass.cardactivity.util.ModelMapper.convertDTOToCardHolder;

@RestController
@RequestMapping(value = "/cards")
public class CardHolderController {
    private final CardHolderService cardHolderService;

    @Autowired
    public CardHolderController(CardHolderService cardHolderService) {
        this.cardHolderService = cardHolderService;
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CardHolderDTO createCardHolder(@RequestBody CardHolderDTO cardHolderDTO) {
        CardHolder createCardHolder = cardHolderService.createCardHolder(convertDTOToCardHolder(cardHolderDTO));
        return convertCardHolderTODTO(createCardHolder);
    }

//    @PostMapping
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public CardHolderDTO createCardHolder(@RequestBody CardHolderDTO cardHolderDTO) {
//        CardHolder createCardHolder = cardHolderService.createCard(convertDTOToCardHolder(cardHolderDTO));
//        return convertToResponseDTO(createCardHolder);
//    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CardHolderDTO> getAllCardHolder() {
        List<CardHolder> listCardHolder = cardHolderService.getAllCardsHolders();
        //A mesma coisa aqui, você está convertendo um objeto e então retornando ele, mas você consegue retornar diretamente a conversão
        return listCardHolder.stream()
                .map(ModelMapper::convertCardHolderTODTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/cardholder/{documentNumber}")
    @ResponseStatus(code = HttpStatus.OK)
    public CardHolderDTO getByCardHolderDocumentNumber(@PathVariable String documentNumber) {
        CardHolder cardHolder = cardHolderService.getByCardHolderDocumentNumber(documentNumber);
        return convertCardHolderTODTO(cardHolder);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(code = HttpStatus.OK)
    //Como você está atualizando um objeto e não criando, o status code que vc deve retornar é um OK
    public CardHolderDTO updateCardHolder(@PathVariable Long id,
                                          //Aqui entra o caso do dto que comentei, no service você só atualiza o nome,
                                          // então deveria ter um "CardHolderUpdateRequest" somente com "name"
                                          // para receber aqui no body
                                          @RequestBody(required = false) CardHolder updatedCardHolder) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para atualizar"); //Você está fazendo essa validação no service também, ai fica redundante .. poderia deixar somente lá e remover essa
        }

        try {

            CardHolder updatedResult = cardHolderService.updateCardHolder(id, updatedCardHolder);

            return convertCardHolderTODTO(updatedResult);
        } catch (EmptyResultDataAccessException e) {
            throw new CardNotFoundExceptions(id);
        } //Esse try/catch deveria ficar dentro do seu service, pois é lá que você faz a tentativa de comunicação com banco de dados
    }

    @PatchMapping("/{documentNumber}")
    @ResponseStatus(HttpStatus.OK)
    public CardHolderDTO updateCardStatusByDocumentNumber(
            @PathVariable String documentNumber,
            @RequestBody UpdateCardStatusRequestDTO updateCardStatusRequest) {
        CardHolder cardHolder = cardHolderService.updateCardStatusByDocumentNumber(
                documentNumber,
                updateCardStatusRequest.getActive()
        );
        return ModelMapper.convertCardHolderTODTO(cardHolder);
    }

//    @PatchMapping("/{documentNumber}")
//    @ResponseStatus(HttpStatus.OK)
//    public CardHolderDTO updateCardStatusByDocumentNumber(
//            @PathVariable String documentNumber,
//            @RequestParam(defaultValue = "false") @RequestBody boolean activate) {
//        //Aqui não deveria ser um "requestParam" mas ter um objeto "updateCardStatusRequest" com "active" do tipo boolean e receber usando o @RequestBody){
//            CardHolder cardHolder = cardHolderService.getByCardHolderDocumentNumber((documentNumber, activate);
//            //Esse Validate aqui não é necessário, pois no updateCardStatusByDocumentNumber você busca o usuário de dentro do database. logo se não cair na exception o usuãrio é válido
//            validateCardHolder(cardHolder);
//            cardHolder.getCard().setCardActive(activate);
//            return ModelMapper.convertToResponseDTO(cardHolder);
//        }

    @DeleteMapping(value = "/{id}/deletedcard")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteIdCardHolder (@PathVariable Long id){
        cardHolderService.deleteCardHolder(id);
    }
}