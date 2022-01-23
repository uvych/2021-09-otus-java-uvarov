package ru.otus.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{

    private static final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);

    private final Class<T> tClass;

    private String nameCache = null;

    private Constructor<T> constructorCache = null;

    private Field idFieldCache = null;

    private final List<Field> allFieldsCache = new ArrayList<>();

    private final List<Field> fieldsWithOutIdCache = new ArrayList<>();

    public EntityClassMetaDataImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public String getName()  {
        if (nameCache == null) {
            nameCache = tClass.getSimpleName();
            return nameCache;
        }
       return nameCache;
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructorCache == null) {
            try {
                constructorCache = tClass.getConstructor();
                return constructorCache;
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage());
            }
        }

        return constructorCache;
    }

    @Override
    public Field getIdField() {
        if (idFieldCache == null) {
            Field [] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                Annotation [] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().equals(Id.class)) {
                        idFieldCache = field;
                        return idFieldCache;
                    }
                }
            }
        }
        return idFieldCache;
    }

    @Override
    public List<Field> getAllFields() {
        if (allFieldsCache.isEmpty()) {
            allFieldsCache.addAll(List.of(tClass.getDeclaredFields()));
        }
        return allFieldsCache;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithOutIdCache.isEmpty()) {
            fieldsWithOutIdCache.addAll(
                getAllFields().stream()
                .filter((field) -> !field.equals(getIdField())).toList()
            );
        }
        return fieldsWithOutIdCache;
    }
}
