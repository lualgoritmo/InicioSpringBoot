package com.lucianobass.cardactivity.services;

import com.lucianobass.cardactivity.models.Card;
import com.lucianobass.cardactivity.models.CardHolder;
import com.lucianobass.cardactivity.repositories.CardHolderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lucianobass.cardactivity.models.CardHolder.genereateNumberAleatory;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CardHolderServiceTest {

    @InjectMocks
    private CardHolderService cardHolderService;
    @Mock
    private CardHolderRepository cardHolderRepository;

    @Test
    void createCard() {
        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1942-10-01");
        cardHolder.setCard(new Card(null,
                genereateNumberAleatory(16),
                "30/02",
                "100.00",
                100.00,
                "123",
                false,
                null,
                null));

        when(cardHolderRepository.save(cardHolder)).thenReturn(cardHolder);
        CardHolder saveCardHolder = cardHolderService.createCard(cardHolder);

        assertThat(saveCardHolder).isEqualTo(cardHolder);

        verify(cardHolderRepository, times(1)).save(cardHolder);
    }

    @Test
    void getAllCardsHolders() {
//        List<CardHolder> cardHolders = Arrays.asList(new CardHolder(), new CardHolder());
        List<CardHolder> cardHolders = new ArrayList<>();
        cardHolders.add(new CardHolder());
        cardHolders.add(new CardHolder());
        cardHolders.add(new CardHolder());

        when(cardHolderRepository.findAll()).thenReturn(cardHolders);

        List<CardHolder> simulatedCardHolder = cardHolderService.getAllCardsHolders();

        assertThat(simulatedCardHolder).isEqualTo(cardHolders);
        verify(cardHolderRepository, times(1)).findAll();
    }

    @Test
    void getByIdCardHolder() {
        CardHolder cardHolder = new CardHolder();
        when(cardHolderRepository.findById(cardHolder.getIdCardHolder())).thenReturn(Optional.of(cardHolder));
        cardHolder.setName("Luciano");
        cardHolder.setDocumentNumber("12345678910");
        cardHolder.setBirthDate("1945-10-10");
        cardHolderService.createCard(cardHolder);

        CardHolder simuledCardHolder = cardHolderService.getByIdCardHolder(cardHolder.getIdCardHolder());

        assertThat(simuledCardHolder).isEqualTo(cardHolder);

        verify(cardHolderRepository, times(1)).findById(cardHolder.getIdCardHolder());
    }

    @Test
    void getByCardHolderDocumentNumber() {
    }

    @Test
    void deleteIdCard() {
    }

    @Test
    void updateCardHolder() {
    }

    @Test
    void updateCardStatusByDocumentNumber() {
    }

    @Test
    void updateLimitCard() {
    }
}