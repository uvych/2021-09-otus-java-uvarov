package ru.otus.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<SQLCommand, String> cacheSQL = new HashMap<>();

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String sql = cacheSQL.get(SQLCommand.SELECT_ALL);
        if (sql == null) {
            sql = SELECT + ALL + FROM +
                entityClassMetaData.getName().toLowerCase();
            cacheSQL.put(SQLCommand.SELECT_ALL, sql);
        }
        return sql;
    }

    @Override
    public String getSelectByIdSql() {
        String sql = cacheSQL.get(SQLCommand.SELECT_BY_ID);
        if (sql == null) {
            sql = SELECT + getSelectParameter(entityClassMetaData.getAllFields())
                + FROM + entityClassMetaData.getName()
                + WHERE + entityClassMetaData.getIdField().getName() + EQUAL_SING + MARK;
            cacheSQL.put(SQLCommand.SELECT_BY_ID, sql);
        }
        return sql;
    }

    @Override
    public String getInsertSql() {
        String sql = cacheSQL.get(SQLCommand.INSERT);
        if (sql == null) {
            List<Field> fields = entityClassMetaData.getFieldsWithoutId();
            sql = INSERT + entityClassMetaData.getName().toLowerCase() + PARENTHESES_LEFT
                + getSelectParameter(fields) + PARENTHESES_RIGHT
                + VALUES + PARENTHESES_LEFT + getMarks(fields.size()) + PARENTHESES_RIGHT;
            cacheSQL.put(SQLCommand.INSERT, sql);
        }
        return sql;
    }

    @Override
    public String getUpdateSql() {
        String sql = cacheSQL.get(SQLCommand.UPDATE);
        if (sql == null) {
            sql = UPDATE + entityClassMetaData.getName().toLowerCase()
                + SET + getUpdateParameter() + WHERE + entityClassMetaData.getIdField() + EQUAL_SING + MARK;
            cacheSQL.put(SQLCommand.UPDATE, sql);
        }
        return sql;
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
