package ru.otus.mapper;

import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.model.dto.ClientDTO;

import java.util.stream.Collectors;

public class ClientMapper {

    public static ClientDTO getDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .address(client.getAddress().getStreet())
                .phones(client.getPhone().stream()
                    .map(Phone::getNumber)
                    .collect(Collectors.toList()))
                .build();
    }

    public static Client getClient(ClientDTO clientDTO) {
        var phones = clientDTO.getPhones().stream()
            .map(str -> new Phone(null, str))
            .collect(Collectors.toList());
        return new Client(null, clientDTO.getName(),
                new Address(null, clientDTO.getAddress()), phones);
    }
}
