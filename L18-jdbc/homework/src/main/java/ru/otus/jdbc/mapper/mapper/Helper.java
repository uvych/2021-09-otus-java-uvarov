package ru.otus.jdbc.mapper.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface Helper<T> {
    List<Object> getFieldsValues(T objectData);
    T getObject(ResultSet rs);
    List<Object> getFieldsValuesWithoutID(T objectData);
}
