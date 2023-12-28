package com.lucianobass.cardactivity.controller;

import com.lucianobass.cardactivity.controllerresources.controller.CardHolderController;
import com.lucianobass.cardactivity.services.CardHolderService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(CardHolderController.class)
class CardHolderControllerTest {

    @InjectMocks
    private CardHolderController cardHolderController;

    @Mock
    private CardHolderService cardHolderService;


}
