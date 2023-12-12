package com.lucianobass.cardactivity.util;

import com.lucianobass.cardactivity.controller.dto.CreateAddressRequestDTO;
import com.lucianobass.cardactivity.controller.dto.CreateUserRequestDTO;
import com.lucianobass.cardactivity.controller.dto.CreateUserResponseDTO;
import com.lucianobass.cardactivity.model.Address;
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

    public static CreateUserRequestDTO converUserClientToRequestDTO(UserClient userClient) {
        CreateUserRequestDTO userClientRequestDTO = new CreateUserRequestDTO(
                userClient.getIdUser(),
                userClient.getName(),
                userClient.getAge(),
                userClient.getGenere(),
                userClient.getAddress()
        );

        return userClientRequestDTO;
    }

    public static List<CreateUserResponseDTO> responseListUserClient(List<UserClient> userClients) {
        return userClients.stream().map(ModelMapper::convertUserClientToResponseDTO)
                .collect(Collectors.toList());
    }

    public static CreateUserResponseDTO convertUserClientToResponseDTO(UserClient userClient) {
        CreateUserResponseDTO userClientResponseDTO = new CreateUserResponseDTO(
                userClient.getName(),
                userClient.getAge(),
                userClient.getGenere()
        );
        return userClientResponseDTO;
    }

    public static CreateUserResponseDTO convertRequestDTOTOResponseDTO(CreateUserRequestDTO createUserRequestDTO) {
        CreateUserResponseDTO userClientResponseDTO = new CreateUserResponseDTO(
                createUserRequestDTO.getName(),
                createUserRequestDTO.getAge(),
                createUserRequestDTO.getGenere()
        );
        return userClientResponseDTO;
    }

    public static Address convertRequestAddressDTOToAddress(CreateAddressRequestDTO createAddressRequestDTO) {
        Address address = new Address(
                createAddressRequestDTO.getCep(),
                createAddressRequestDTO.getNumberHome(),
                createAddressRequestDTO.getRoad(),
                createAddressRequestDTO.getCity(),
                createAddressRequestDTO.getCity(),
                createAddressRequestDTO.getUF());

        return address;
    }

    public static CreateAddressRequestDTO convertAddresToRequestDTO(Address address) {
        CreateAddressRequestDTO createAddressRequestDTO = new CreateAddressRequestDTO(
                address.getCep(),
                address.getNumberHome(),
                address.getRoad(),
                address.getCity(),
                address.getUF()
        );
        return createAddressRequestDTO;
    }

    public static UserClient convertRequestDTOTOUserClient(CreateUserRequestDTO createUserRequestDTO) {
        UserClient userClient = new UserClient(
                createUserRequestDTO.getName(),
                createUserRequestDTO.getAge(),
                createUserRequestDTO.getGenere());

        return userClient;
    }
}
