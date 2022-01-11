package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{

    private static final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);

    private final Class<T> tClass;

    public EntityClassMetaDataImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public String getName()  {
       return tClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return tClass.getConstructor();
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Field getIdField() {
        Field [] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            Annotation [] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Id.class)) {
                    return field;
                }
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(tClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(tClass.getDeclaredFields())
            .filter((field) -> !field.equals(getIdField())).toList();
    }
}
