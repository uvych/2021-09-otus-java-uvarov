package ru.otus.core.repository.executor;

import java.sql.*;
import java.util.*;
import java.util.function.*;

public interface DbExecutor {

    long executeStatement(Connection connection, String sql, List<Object> params);

    <T> Optional<T> executeSelect(Connection connection, String sql, List<Object> params, Function<ResultSet, T> rsHandler) ;
}
