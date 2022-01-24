package ru.otus.services;

import ru.otus.model.*;

import java.util.*;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
