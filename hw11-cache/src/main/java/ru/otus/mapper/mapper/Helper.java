package ru.otus.mapper.mapper;

import java.sql.*;
import java.util.*;

public interface Helper<T> {
    List<Object> getFieldsValues(T objectData);
    T getObject(ResultSet rs);
    List<Object> getFieldsValuesWithoutID(T objectData);
}
