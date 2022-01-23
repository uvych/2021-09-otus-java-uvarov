package ru.otus.core.sessionmanager;

import java.sql.*;
import java.util.function.*;

public interface TransactionAction<T> extends Function<Connection, T> {
}
