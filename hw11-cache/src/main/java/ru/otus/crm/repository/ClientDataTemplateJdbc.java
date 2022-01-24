package ru.otus.crm.repository;

import ru.otus.core.repository.*;
import ru.otus.core.repository.executor.*;
import ru.otus.crm.model.*;

import java.sql.*;
import java.util.*;

public class ClientDataTemplateJdbc implements DataTemplate<Client> {

    private final DbExecutor dbExecutor;

    public ClientDataTemplateJdbc(DbExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Client> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, "select id, name from client where id  = ?", List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return new Client(rs.getLong("id"), rs.getString("name"));
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<Client> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, "select * from client", Collections.emptyList(), rs -> {
            var clientList = new ArrayList<Client>();
            try {
                while (rs.next()) {
                    clientList.add(new Client(rs.getLong("id"), rs.getString("name")));
                }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, Client client) {
        try {
            return dbExecutor.executeStatement(connection, "insert into client(name) values (?)",
                    Collections.singletonList(client.getName()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, Client client) {
        try {
            dbExecutor.executeStatement(connection, "update client set name = ? where id = ?",
                    List.of(client.getName(), client.getId()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
