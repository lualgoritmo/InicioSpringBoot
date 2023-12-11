package com.lucianobass.cardactivity.controller;

import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.CreateUserResponseDTO;
import com.lucianobass.cardactivity.service.UserClientService;
import com.lucianobass.cardactivity.util.ModelMapper;
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
    public CreateUserResponseDTO createUserClient(
            @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        CreateUserRequestDTO createRequestUserDTO = userClientService.createUserClient(createUserRequestDTO);
        return ModelMapper.convertRequestDTOTOResponseDTO(createRequestUserDTO);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CreateUserResponseDTO> getUserClientsAll() {
        return userClientService.getUserClientsAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CreateUserResponseDTO getUserClientByid(@PathVariable Long id) {
        if (id == null) {
            System.out.println("O usuário não existe!");
        }
        try {
            CreateUserResponseDTO userClientResponseDTO = userClientService.getUserClientById(id);
            return userClientResponseDTO;

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
