package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private static final String SELECT = "select\s";
    private static final String FROM = "\sfrom\s";
    private static final String ALL = "\s*\s";
    private static final String WHERE = "\swhere\s";
    private static final String EQUAL_SING = "\s=\s";
    private static final String MARK = "?";
    private static final String INSERT = "insert into\s";
    private static final String PARENTHESES_LEFT = "\s(";
    private static final String PARENTHESES_RIGHT = ")\s";
    private static final String VALUES = "values";
    private static final String UPDATE = "update\s";
    private static final String SET = "\sset\s";

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaDataImpl<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return SELECT + ALL + FROM +
            entityClassMetaData.getName().toLowerCase();
    }

    @Override
    public String getSelectByIdSql() {
        return SELECT + getSelectParameter(entityClassMetaData.getAllFields())
            + FROM + entityClassMetaData.getName() + WHERE + entityClassMetaData.getIdField().getName() + EQUAL_SING + MARK;
    }

    @Override
    public String getInsertSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        return INSERT + entityClassMetaData.getName().toLowerCase() + PARENTHESES_LEFT
            + getSelectParameter(fields) + PARENTHESES_RIGHT
            + VALUES + PARENTHESES_LEFT + getMarks(fields.size()) + PARENTHESES_RIGHT;
    }

    @Override
    public String getUpdateSql() {
        return UPDATE + entityClassMetaData.getName().toLowerCase()
            + SET + getUpdateParameter() + WHERE + entityClassMetaData.getIdField() + EQUAL_SING + MARK;
    }

    private String getSelectParameter(List<Field> fields) {
        var result = new StringBuilder();
        int size = fields.size();
        for (int i = 0; i < size - 1; i++) {
            result.append(fields.get(i).getName())
                .append(",\s");
        }
        result.append(fields.get(size - 1).getName());
        return result.toString();
    }

    private String getMarks(int count) {
        var result = new StringBuilder();
        for (int i = 0; i < count - 1; i++) {
            result.append(MARK)
                .append(",\s");
        }
        result.append(MARK);
        return result.toString();
    }

    private String getUpdateParameter() {
        var result = new StringBuilder();
        var fieldList = entityClassMetaData.getAllFields();
        int size = fieldList.size();
        for (int i = 0; i < size - 1; i++) {
            result.append(fieldList.get(i).getName()).append(" = ?,");
        }
        result.append(fieldList.get(size - 1).getName()).append(" = ?");
        return result.toString();
    }
}
