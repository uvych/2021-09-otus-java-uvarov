package ru.otus.services;

import ru.otus.model.*;
import ru.otus.model.dto.*;

import java.util.*;

public interface DBServiceClient {

    Client saveClient(ClientDTO clientDTO);

    Optional<Client> getClient(long id);

    List<ClientDTO> findAll();
}
