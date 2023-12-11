package com.lucianobass.cardactivity.controller;

import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientResponseDTO;
import com.lucianobass.cardactivity.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public UserClientResponseDTO createUserClient(
            @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        UserClientResponseDTO userClientResponseDTO = userClientService.createUserClient(createUserRequestDTO);
        return userClientResponseDTO;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserClientResponseDTO> getUserClientsAll() {
        return userClientService.getUserClientsAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserClientResponseDTO getUserClientByid(@PathVariable Long id) {
        if (id == null) {
            System.out.println("O usuário não existe!");
        }
        try {
            UserClientResponseDTO userClientResponseDTO = userClientService.getUserClientById(id);
                return userClientResponseDTO;

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
