package ru.otus.core.repository;

import java.sql.*;
import java.util.*;

public interface DataTemplate<T> {
    Optional<T> findById(Connection connection, long id);

    List<T> findAll(Connection connection);

    long insert(Connection connection, T object);

    void update(Connection connection, T object);
}
