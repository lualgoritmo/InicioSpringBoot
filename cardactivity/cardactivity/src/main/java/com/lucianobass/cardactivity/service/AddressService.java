package com.lucianobass.cardactivity.service;

import com.lucianobass.cardactivity.controller.dto.CreateAddressRequestDTO;
import com.lucianobass.cardactivity.model.Address;
import com.lucianobass.cardactivity.model.UserClient;
import com.lucianobass.cardactivity.repositories.AddressRepository;
import com.lucianobass.cardactivity.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.lucianobass.cardactivity.util.ModelMapper.convertAddresToRequestDTO;
import static com.lucianobass.cardactivity.util.ModelMapper.convertRequestDTOTOUserClient;

@Service
public class AddressService {
    private final UserClientService userClientService;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(UserClientService userClientService, AddressRepository addressRepository) {
        this.userClientService = userClientService;
        this.addressRepository = addressRepository;
    }

    public CreateAddressRequestDTO createAddress(@NonNull Long idUserClient, CreateAddressRequestDTO createAddressRequestDTO) {
        Objects.requireNonNull(idUserClient, "idUserClient não pode ser nulo");
        if (createAddressRequestDTO == null) {
            throw new IllegalArgumentException("Parâmetrosinválidado para criar o endereço.");
        }

        Address address = ModelMapper.convertRequestAddressDTOToAddress(createAddressRequestDTO);
        UserClient userClient = convertRequestDTOTOUserClient(userClientService.getByIdUserClient(idUserClient));

        if (address == null && userClient == null) {
            throw new IllegalArgumentException("Endereço não criado!");
        }
        address.setUser(userClient);
        addressRepository.save(address);
        return convertAddresToRequestDTO(address);
    }

}
