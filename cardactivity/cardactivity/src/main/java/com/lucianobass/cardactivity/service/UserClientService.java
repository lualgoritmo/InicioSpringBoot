package com.lucianobass.cardactivity.service;

import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientResponseDTO;
import com.lucianobass.cardactivity.model.UserClient;
import com.lucianobass.cardactivity.repositories.UserClientRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserClientService {

    private UserClientRepository userClientRepository;

    @Autowired
    public void UserService(UserClientRepository userClientRepository) {
        this.userClientRepository = userClientRepository;
    }

    @Transactional
    public UserClientResponseDTO createUserClient(CreateUserRequestDTO requestDTO) {
        UserClient userClient = ModelMapper.convertResquestDTOToUser(requestDTO);
        UserClient newUserCLient = new UserClient(
                requestDTO.getName(),
                requestDTO.getAge(),
                requestDTO.getGenere()
        );
        if (userClient == null) {
            throw new IllegalArgumentException("Não foi possível cadastrar o cliente");
        }

        try {
            return ModelMapper.convertUserClientToResponseDTO(userClientRepository.save(newUserCLient));
        } catch (Exception ex) {
            System.out.println("Erro ao criar o cliente");
            throw ex;
        }

    }
}
