package ru.otus.model.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ClientDTO {

    private Long id;

    private String name;

    private String address;

    private List<String> phones;
}
