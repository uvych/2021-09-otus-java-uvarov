package ru.otus.crm.service;

import ru.otus.crm.model.*;

import java.util.*;

public interface DBServiceManager {

    Manager saveManager(Manager client);

    Optional<Manager> getManager(long no);

    List<Manager> findAll();
}
