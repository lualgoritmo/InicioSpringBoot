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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CardHolderServiceTest {

    @Mock
    private CardHolderRepository cardHolderRepository;
    @InjectMocks
    private CardHolderService cardHolderService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void createCard() {
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
    void getAllCardsHolders() {
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
    void getByIdCardHolder() {
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
    void getByCardHolderDocumentNumber() {
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
        verify(cardHolderRepository, times(1)).deleteById(1L);
    }

//    @Test
//    void updateCardHolder() {
//    }
//
//    @Test
//    void updateCardStatusByDocumentNumber() {
//    }

//    @Test
//    void updateLimitCardTest() {
//        // Criar um CardHolder sem definir o id explicitamente
//        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1983-10-10");
//        cardHolder.getCard().setCardActive(true);
//
//        // Salvar o CardHolder e obter o id atribuído
//        cardHolder = cardHolderService.createCardHolder(cardHolder);
//
//        // Chamar o método updateLimitCard
//        cardHolderService.updateLimitCard(anyLong(), cardHolder.getCard());
//
//        // Verificar interação com o repositório
//        verify(cardHolderRepository, times(1)).save(any(CardHolder.class));
//    }


//    @Test
//    void updateLimitCard() {
//        // Criar um CardHolder sem definir o id explicitamente
//        CardHolder cardHolder = new CardHolder("Luciano", "12345678910", "1983-10-10");
//        cardHolder.setCard(new Card(null,
//                "1234123412341234", // Use o mesmo padrão para o número do cartão
//                "30/02",
//                "100.00",
//                200.00,
//                "123",
//                true, // Certifique-se de que o cartão está ativo para evitar a exceção
//                null,
//                null));
//
//        // Salvar o CardHolder e obter o id atribuído
//        cardHolder = cardHolderService.createCardHolder(cardHolder);
//
//        // Adicione logs para verificar o status do cardHolder antes de chamar o método
//        System.out.println("ID do CardHolder antes da chamada de updateLimitCard: " + cardHolder.getIdCardHolder());
//        System.out.println("CardHolder antes da chamada de updateLimitCard: " + cardHolder);
//
//        // Chamar o método updateLimitCard
//        cardHolderService.updateLimitCard(cardHolder.getIdCardHolder(), cardHolder.getCard());
//
//        // Adicione logs para verificar o status do cardHolder após a chamada do método
//        System.out.println("ID do CardHolder após a chamada de updateLimitCard: " + cardHolder.getIdCardHolder());
//        System.out.println("CardHolder após a chamada de updateLimitCard: " + cardHolder);
//
//        // Verificar interação com o repositório
//        verify(cardHolderRepository, times(1)).save(any(CardHolder.class));
//    }


}