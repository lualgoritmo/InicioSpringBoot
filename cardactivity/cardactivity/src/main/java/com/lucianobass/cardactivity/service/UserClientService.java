package com.lucianobass.cardactivity.service;

import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientResponseDTO;
import com.lucianobass.cardactivity.exceptions.UserClientNotFoundException;
import com.lucianobass.cardactivity.model.UserClient;
import com.lucianobass.cardactivity.repositories.UserClientRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.lucianobass.cardactivity.util.ModelMapper.*;

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

        if (userClient == null) {
            throw new IllegalArgumentException("Não foi possível cadastrar o cliente");
        }

        try {
            return convertUserClientToResponseDTO(userClientRepository.save(userClient));
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Erro ao criar o cliente: Dados " +
                    "duplicados ou violação de integridade.");
        } catch (Exception ex) {
            System.out.println("Erro ao criar o cliente");
            throw ex;
        }

    }

    @Transactional
    public List<UserClientResponseDTO> getUserClientsAll() {
        List<UserClient> userClients = userClientRepository.findAll();
        return responseListUserClient(userClients);
    }

    @Transactional
    public UserClientResponseDTO getUserClientById(Long idUserClient) {
        UserClient userClient = userClientRepository.findById(idUserClient)
                .orElseThrow(() -> new UserClientNotFoundException(idUserClient));
        return convertUserClientToResponseDTO(userClient);
    }
}
