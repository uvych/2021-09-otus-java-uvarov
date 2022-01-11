package ru.otus.jdbc.mapper.mapper;

import ru.otus.core.repository.DataTemplateException;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstanceHelper<T> {
    private final EntityClassMetaDataImpl<T> classMetaData;

    public InstanceHelper(EntityClassMetaDataImpl<T> classMetaData) {
        this.classMetaData = classMetaData;
    }

    public T getObject(ResultSet rs) {
        T obj = createEmptyObject();
        mapFields(rs, obj);
        return obj;
    }

    private T createEmptyObject()  {
        var constructor = classMetaData.getConstructor();
        Object[] params = new Object[0];
        try {
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DataTemplateException(e);
        }
    }

    private void mapFields(ResultSet rs, T instance) {
        for (Field field : classMetaData.getAllFields()) {
            var fieldName = field.getName();
            field.setAccessible(true);
            Object value;
            try {
                value = rs.getObject(fieldName);
                field.set(instance, value);
            } catch (IllegalAccessException | SQLException e) {
                throw new DataTemplateException(e);
            }
        }
    }

    public List<Object> getFieldsValues(T objectData) {
        var values = new ArrayList<>();
        for (Field field : classMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            try {
                values.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }
        return values;
    }
}
