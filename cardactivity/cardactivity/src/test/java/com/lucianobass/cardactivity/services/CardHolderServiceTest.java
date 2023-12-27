package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lucianobass.cardactivity.models.CardHolder.generateNumberAleatory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CardHolderServiceTest {
    @InjectMocks
    private CardHolderService cardHolderService;
    @Mock
    private CardHolderRepository cardHolderRepository;

    @Test
    void createCardTest() {
        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1942-10-01");
        cardHolder.setCard(new Card(null,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                false,
                null,
                null));

        when(cardHolderRepository.save(cardHolder)).thenReturn(cardHolder);
        CardHolder saveCardHolder = cardHolderService.createCardHolder(cardHolder);

        assertThat(saveCardHolder).isEqualTo(cardHolder);

        verify(cardHolderRepository, times(1)).save(cardHolder);
    }

    @Test
    void getAllCardsHoldersTest() {
//        List<CardHolder> cardHolders = Arrays.asList(new CardHolder(), new CardHolder());
        List<CardHolder> cardHolders = new ArrayList<>();
        CardHolder cardHolderOne = new CardHolder("Luciano", "123456789", "1983-10-10");
        CardHolder cardHolderTwo = new CardHolder("Maria", "123456789", "1963-02-12");
        CardHolder cardHolder = new CardHolder();
//        cardHolder.setName("José");
//        cardHolder.setDocumentNumber("00000000000");
//        cardHolder.setBirthDate("1600-02-02");
        cardHolders.add(new CardHolder());
        cardHolders.add(new CardHolder());
        cardHolders.add(new CardHolder());
        ModelMapper.validateCardHolder(cardHolderOne);
        when(cardHolderRepository.findAll()).thenReturn(cardHolders);
        cardHolderService.createCardHolder(cardHolder);
        cardHolderService.createCardHolder(cardHolderOne);
        cardHolderService.createCardHolder(cardHolderTwo);

        List<CardHolder> simulatedCardHolder = cardHolderService.getAllCardsHolders();

        assertThat(simulatedCardHolder).isEqualTo(cardHolders);
        assertFalse(simulatedCardHolder.isEmpty(), "Falso");
        assertThat(3).isEqualTo(simulatedCardHolder.size());

        assertThat(simulatedCardHolder.size() == 0).isEqualTo(false);
        System.out.println(simulatedCardHolder + "diferente de zero");
        assertTrue(simulatedCardHolder.size() > 0, "É verdadeiro");
        System.out.println(simulatedCardHolder + "maior que zero");
        assertFalse(simulatedCardHolder.size() < 0, "FALSO");
        assertThat(simulatedCardHolder.size() < 0).isEqualTo(false);
        System.out.println(simulatedCardHolder + "É FALSO E MAIOR QUE ZEROo");
        verify(cardHolderRepository, times(1)).findAll();
    }

    @Test
    void getByIdCardHolderTest() {
        CardHolder cardHolder = new CardHolder();
        when(cardHolderRepository.findById(cardHolder.getIdCardHolder())).thenReturn(Optional.of(cardHolder));
        cardHolder.setName("Luciano");
        cardHolder.setDocumentNumber("12345678910");
        cardHolder.setBirthDate("1945-10-10");
        cardHolderService.createCardHolder(cardHolder);

        CardHolder simuledCardHolder = cardHolderService.getByIdCardHolder(cardHolder.getIdCardHolder());

        assertThat(simuledCardHolder).isEqualTo(cardHolder);

        verify(cardHolderRepository, times(1)).findById(cardHolder.getIdCardHolder());
    }

    @Test
    void getByCardHolderDocumentNumberTest() {
        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1983-10-10");
        when(cardHolderRepository.findByDocumentNumber(cardHolder.getDocumentNumber()))
                .thenReturn(cardHolder);
        cardHolderService.createCardHolder(cardHolder);

        CardHolder getCardHolder = cardHolderService.getByCardHolderDocumentNumber(cardHolder.getDocumentNumber());

        assertThat(getCardHolder.getDocumentNumber()).isEqualTo(cardHolder.getDocumentNumber());

        verify(cardHolderRepository, times(1)).findByDocumentNumber(cardHolder.getDocumentNumber());
    }

    @Test
    void deleteCardHolderTest() {
        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1983-10-10");
        cardHolderService.createCardHolder(cardHolder);
        cardHolder.setIdCardHolder(1L);
//        cardHolder.setCard(new Card(null,
//                genereateNumberAleatory(16),
//                "30/02",
//                "100.00",
//                100.00,
//                "123",
//                false,
//                null,
//                null));


        System.out.println("CardHolder existente: " + cardHolder.getIdCardHolder());
        cardHolderService.deleteCardHolder(1L);
        System.out.println("CardHolder para excluir: " + cardHolder.getIdCardHolder());
        verify(cardHolderRepository, times(1)).deleteById(any());
    }

    @Test
    void updateCardHolderTest() {
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        cardHolder.setIdCardHolder(1L);
        cardHolder.getCard().setIdCard(1L);

        when(cardHolderRepository.findById(any())).thenReturn(Optional.of(cardHolder));
        CardHolder testUpdateCardHolder = new CardHolder("Novo Nome Atualizado", "123456789", "1983-10-10");
        testUpdateCardHolder.setIdCardHolder(1L);

        when(cardHolderRepository.save(any())).thenReturn(testUpdateCardHolder);
        CardHolder savedCardHolder = cardHolderService.updateCardHolder(cardHolder.getIdCardHolder(), testUpdateCardHolder);

        assertThat(cardHolder).isNotNull();
        assertThat(savedCardHolder).isNotNull();
        assertThat(testUpdateCardHolder).isNotNull();
        assertEquals(testUpdateCardHolder.getName(), cardHolder.getName());
        assertEquals(savedCardHolder, testUpdateCardHolder);
        verify(cardHolderRepository, times(1)).save(any());
        verify(cardHolderRepository, times(1)).findById(any());
    }

    @Test
    void updateCardStatusByDocumentNumberTest() {
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        cardHolder.setIdCardHolder(1L);
        cardHolder.setCard(new Card(1L,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                false,
                null,
                null));

        when(cardHolderRepository.findByDocumentNumber(cardHolder.getDocumentNumber()))
                .thenReturn(cardHolder);

        CardHolder statusCardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        cardHolder.setIdCardHolder(1L);
        cardHolder.setCard(new Card(1L,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                true,
                null,
                null));
        statusCardHolder.getCard().setCardActive(true);
        when(cardHolderRepository.save(any())).thenReturn(statusCardHolder);

        CardHolder updateStatusCardHolder = cardHolderService.updateCardStatusByDocumentNumber(
                cardHolder.getDocumentNumber(), statusCardHolder.getCard().getCardActivate());

        assertThat(cardHolder).isNotNull();
        assertThat(statusCardHolder).isNotNull();
        assertThat(updateStatusCardHolder).isNotNull();
        assertEquals(updateStatusCardHolder.getCard().getCardActive(), cardHolder.getCard().getCardActive());
        assertThat(updateStatusCardHolder.getCard().getCardActive()).isEqualTo(true);

        verify(cardHolderRepository, times(1)).save(any());
    }

    @Test
    void updateLimitCardTest() {
        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        cardHolder.setIdCardHolder(1L);
        cardHolder.getCard().setCardActive(true);
        cardHolder.setCard(new Card(1L,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                true,
                null,
                null));

        when(cardHolderRepository.findById(cardHolder.getIdCardHolder()))
                .thenReturn(Optional.of(cardHolder));

        CardHolder updateCardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
        updateCardHolder.setIdCardHolder(1L);
        updateCardHolder.getCard().setCardActive(true);
        updateCardHolder.setCard(new Card(1L,
                generateNumberAleatory(16),
                "30/02",
                "100.00",
                500.00,
                "123",
                true,
                null,
                null));

        when(cardHolderRepository.save(any())).thenReturn(updateCardHolder);

        cardHolderService.updateLimitCard(updateCardHolder.getIdCardHolder(), updateCardHolder.getCard());

        assertThat(cardHolder.getCard().getCardLimit()).isEqualTo(500.00);
        assertThat(cardHolder.getCard().getCardActive()).isTrue();
        assertEquals(updateCardHolder.getCard().getCardLimit(), cardHolder.getCard().getCardLimit());

        verify(cardHolderRepository, times(1)).save(any());
    }

//    @Test
//    void updateLimitCardTest() {
//        CardHolder cardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
//        cardHolder.setIdCardHolder(1L);
//        cardHolder.getCard().setCardActive(true);
//        cardHolder.setCard(new Card(1L,
//                generateNumberAleatory(16),
//                "30/02",
//                "100.00",
//                100.00,
//                "123",
//                false,
//                null,
//                null));
//
//        when(cardHolderService.getByIdCardHolder(cardHolder.getIdCardHolder())).thenReturn(cardHolder);
//
//        CardHolder updateCardHolder = new CardHolder("Luciano", "123456789", "1983-10-10");
//        cardHolder.setIdCardHolder(1L);
//        cardHolder.getCard().setCardActive(true);
//        cardHolder.setCard(new Card(1L,
//                generateNumberAleatory(16),
//                "30/02",
//                "100.00",
//                500.00,
//                "123",
//                true,
//                null,
//                null));
//
//        when(cardHolderRepository.save(any())).thenReturn(updateCardHolder);
//        cardHolder.setIdCardHolder(1L);
//        cardHolder.getCard().setCardActive(true);
//        cardHolder.getCard().setIdCard(1L);
//        Card card = updateCardHolder.getCard();
//        card.setCardLimit(updateCardHolder.getCard().getCardLimit());
//
//        cardHolderService.updateLimitCard(updateCardHolder.getIdCardHolder(), card);
//
//        assertThat(cardHolder).isNotNull();
//        assertThat(updateCardHolder).isNotNull();
//        assertThat(card).isNotNull();
//        //assertEquals(cardHolder.getName(), cardHolder.getName());
//       // assertEquals(savedCardHolder, testUpdateCardHolder);
//        verify(cardHolderRepository, times(1)).save(any());
//
//    }

}
