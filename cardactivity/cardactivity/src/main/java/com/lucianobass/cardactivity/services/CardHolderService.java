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
//        CardHolder cardHolder = setCardHolder(cardHolderDTO);
//        CardHolder savedCardHolder = cardHolderRepository.save(cardHolder);
//        return convertToResponseDTO(savedCardHolder);
//    }

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

        if (cardHolder.getCard() != null) {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setNumberCard(cardHolder.getCard().getNumberCard());
            cardDTO.setCardExpiration(cardHolder.getCard().getCardExpiration());
            cardDTO.setCardLimit(cardHolder.getCard().getCardLimit());
            cardDTO.setCardCVV(cardHolder.getCard().getCardCVV());
            cardDTO.setAvailableLimit(cardHolder.getCard().getAvailableLimit());

            // Importante: Atualizar cardActive no DTO após a ativação
            cardDTO.setCardActive(cardHolder.getCard().getCardActive());

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

    @Transactional()
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
//
//    @Transactional()
//    public CardHolderDTO activateCard(Long id, @RequestBody CardHolderDTO cardHolderDTO) {
//        if (id == null) {
//            throw new IllegalArgumentException("O ID não pode ser nulo para a atualização");
//        }
//
//        CardHolder cardHolder = cardHolderRepository.findById(id).orElseThrow(
//                () -> new CardNotFoundExceptions(id)
//        );
//        cardHolder.getCard().setCardActive(true);
//        BeanUtils.copyProperties(setCardHolder(cardHolderDTO), cardHolder, "id");
//        return convertToResponseDTO(cardHolderRepository.save(cardHolder));
//    }

    // ... outros métodos ...

    @Transactional
    public CardHolderDTO activateCard(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para a ativação");
        }

        try {
            CardHolder cardHolder = cardHolderRepository.findById(id)
                    .orElseThrow(() -> new CardNotFoundExceptions(id));

            // Verifica se o CardHolder possui um Cartão e se o Cartão está inativo
            if (cardHolder.getCard() != null && Boolean.FALSE.equals(cardHolder.getCard().getCardActive())) {
                // Atualiza o status do Cartão para ativo
                cardHolder.getCard().setCardActive(true);

                // Salva as alterações no banco de dados
                cardHolderRepository.save(cardHolder);

                // Retorna o DTO convertido
                return convertToResponseDTO(cardHolder);
            } else if (cardHolder.getCard() != null && Boolean.TRUE.equals(cardHolder.getCard().getCardActive())) {
                // Cartão já está ativo, você pode retornar uma mensagem específica ou nulo
                System.out.println("O cartão já está ativo para o CardHolder com ID: " + id);
                return null;
            } else {
                // Não possui um cartão associado, você pode retornar uma mensagem específica ou nulo
                System.out.println("Não possui um cartão associado para o CardHolder com ID: " + id);
                return null;
            }
        } catch (CardNotFoundExceptions e) {
            // Loga a exceção específica
            System.out.println("CardHolder não encontrado: " + e.getMessage());
            // Rethrow a exceção para que o Spring trate corretamente
            throw e;
        } catch (Exception e) {
            // Loga a exceção genérica
            System.out.println("Erro durante a ativação do CardHolder: " + e.getMessage());
            // Rethrow a exceção para que o Spring trate corretamente
            throw new RuntimeException("Erro durante a ativação do CardHolder", e);
        }
    }

    @Transactional
    public CardHolderDTO deactivateCard(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para a ativação");
        }

        try {
            CardHolder cardHolder = cardHolderRepository.findById(id)
                    .orElseThrow(() -> new CardNotFoundExceptions(id));

            // Verifica se o CardHolder possui um Cartão e se o Cartão está inativo
            if (cardHolder.getCard() != null && Boolean.TRUE.equals(cardHolder.getCard().getCardActive())) {
                // Atualiza o status do Cartão para ativo
                cardHolder.getCard().setCardActive(false);

                // Salva as alterações no banco de dados
                cardHolderRepository.save(cardHolder);

                // Retorna o DTO convertido
                return convertToResponseDTO(cardHolder);
            } else if (cardHolder.getCard() != null && Boolean.FALSE.equals(cardHolder.getCard().getCardActive())) {
                // Cartão já está desativado, você pode retornar uma mensagem específica ou nulo
                System.out.println("O cartão já está desativado para o CardHolder com ID: " + id);
                return null;
            } else {
                // Não possui um cartão associado, você pode retornar uma mensagem específica ou nulo
                System.out.println("Não possui um cartão associado para o CardHolder com ID: " + id);
                return null;
            }
        } catch (CardNotFoundExceptions e) {
            // Loga a exceção específica
            System.out.println("CardHolder não encontrado: " + e.getMessage());
            // Rethrow a exceção para que o Spring trate corretamente
            throw e;
        } catch (Exception e) {
            // Loga a exceção genérica
            System.out.println("Erro durante a desativação do CardHolder: " + e.getMessage());
            // Rethrow a exceção para que o Spring trate corretamente
            throw new RuntimeException("Erro durante a desativação do CardHolder", e);
        }
    }


//
//    @Transactional()
//    public CardHolderDTO activateCard(Long cardHolderId) {
//        try {
//            // Recupera o CardHolder do banco de dados
//            System.out.println("1. Recuperando CardHolder do banco de dados");
//            CardHolder cardHolder = cardHolderRepository.findById(cardHolderId)
//                    .orElseThrow(() -> new CardNotFoundExceptions(cardHolderId));
//
//            // Verifica se o CardHolder possui um Cartão e se o Cartão está inativo
//            if (cardHolder.getCard() != null && !cardHolder.getCard().getCardActive()) {
//                // Atualiza o status do Cartão para ativo
//                System.out.println("2. Atualizando o status do Cartão para ativo");
//                cardHolder.getCard().setCardActive(true);
//
//                // Salva as alterações no banco de dados
//                System.out.println("3. Salvando as alterações no banco de dados");
//                cardHolderRepository.save(cardHolder);
//
//                // Retorna o DTO convertido
//                System.out.println("4. Convertendo para DTO");
//                return convertToResponseDTO(cardHolder);
//            } else if (cardHolder.getCard() != null && cardHolder.getCard().getCardActive()) {
//                // Cartão já está ativo, você pode retornar uma mensagem específica ou nulo
//                System.out.println("O cartão já está ativo para o CardHolder com ID: " + cardHolderId);
//                return null;
//            } else {
//                // Não possui um cartão associado, você pode retornar uma mensagem específica ou nulo
//                System.out.println("Não possui um cartão associado para o CardHolder com ID: " + cardHolderId);
//                return null;
//            }
//        } catch (CardNotFoundExceptions e) {
//            // Loga a exceção específica
//            System.out.println("CardHolder não encontrado: " + e.getMessage());
//            // Rethrow a exceção para que o Spring trate corretamente
//            throw e;
//        } catch (Exception e) {
//            // Loga a exceção genérica
//            System.out.println("Erro durante a ativação do CardHolder: " + e.getMessage());
//            // Rethrow a exceção para que o Spring trate corretamente
//            throw new RuntimeException("Erro durante a ativação do CardHolder", e);
//        }
//    }

}
