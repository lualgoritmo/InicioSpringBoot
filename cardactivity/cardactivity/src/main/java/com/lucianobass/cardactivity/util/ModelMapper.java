package com.lucianobass.cardactivity.util;

import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.UserClientResponseDTO;
import com.lucianobass.cardactivity.model.UserClient;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {

    public static UserClient convertResquestDTOToUser(CreateUserRequestDTO userRequestDTO) {
        UserClient userClient = new UserClient(
                userRequestDTO.getName(),
                userRequestDTO.getAge(),
                userRequestDTO.getGenere());

        return userClient;
    }

    public static List<UserClientResponseDTO> responseListUserClient(List<UserClient> userClients) {
        return userClients.stream().map(ModelMapper::convertUserClientToResponseDTO)
                .collect(Collectors.toList());
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
