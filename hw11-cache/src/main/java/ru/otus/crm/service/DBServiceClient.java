package ru.otus.crm.service;

import ru.otus.crm.model.*;

import java.util.*;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
