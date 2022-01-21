package ru.otus.jdbc.mapper.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstanceHelper<T> implements Helper<T>{
    private static final Logger log = LoggerFactory.getLogger(InstanceHelper.class);
    private final EntityClassMetaData<T> classMetaData;

    public InstanceHelper(EntityClassMetaData<T> classMetaData) {
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
            log.error("create Obj ex");
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
                log.error("Failed map field = {}", field.getName());
                throw new DataTemplateException(e);
            }
        }
    }

    public List<Object> getFieldsValuesWithoutID(T objectData) {
        var values = new ArrayList<>();
        for (Field field : classMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            try {
                values.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                log.error("Failed find value for field = {}", field.getName());
                throw new DataTemplateException(e);
            }
        }
        return values;
    }

    public List<Object> getFieldsValues(T objectData) {
        var values = new ArrayList<>();
        for (Field field : classMetaData.getAllFields()) {
            field.setAccessible(true);
            try {
                values.add(field.get(objectData));
            } catch (IllegalAccessException e) {
                log.error("Failed find value for field = {}", field.getName());
                throw new DataTemplateException(e);
            }
        }
        return values;
    }
}
