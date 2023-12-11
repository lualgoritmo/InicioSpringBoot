package com.lucianobass.cardactivity.util;

import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientResponseDTO;
import com.lucianobass.cardactivity.model.UserClient;

public class ModelMapper {

    public static UserClient convertResquestDTOToUser(CreateUserRequestDTO userRequestDTO) {
        UserClient userClient = new UserClient(
                userRequestDTO.getName(),
                userRequestDTO.getAge(),
                userRequestDTO.getGenere());

        return userClient;
    }

    public static UserClientResponseDTO convertUserClientToResponseDTO(UserClient userClient) {
        UserClientResponseDTO userClientResponseDTO = new UserClientResponseDTO(
                userClient.getName(),
                userClient.getAge(),
                userClient.getGenere()
        );
        return userClientResponseDTO;
    }
}
