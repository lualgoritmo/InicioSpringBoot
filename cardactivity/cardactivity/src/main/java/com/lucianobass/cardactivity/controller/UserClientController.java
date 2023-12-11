package com.lucianobass.cardactivity.controller;

import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientResponseDTO;
import com.lucianobass.cardactivity.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserClientController {
    private final UserClientService userClientService;

    @Autowired
    public UserClientController(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserClientResponseDTO createUserClient(@RequestBody CreateUserRequestDTO createUserRequestDTO) {

    }
}
