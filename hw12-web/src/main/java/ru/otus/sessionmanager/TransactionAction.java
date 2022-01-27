package ru.otus.sessionmanager;

import org.hibernate.*;

import java.util.function.*;

public interface TransactionAction<T> extends Function<Session, T> {
}
