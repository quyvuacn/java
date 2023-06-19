package org.aptech.t2109e.jspservlet.jpa.impl;

import org.apache.commons.lang3.StringUtils;
import org.aptech.t2109e.jspservlet.annotation.Column;
import org.aptech.t2109e.jspservlet.annotation.Entity;
import org.aptech.t2109e.jspservlet.annotation.Id;
import org.aptech.t2109e.jspservlet.utils.select.Pagination;
import org.aptech.t2109e.jspservlet.config.db.DbConnection;
import org.aptech.t2109e.jspservlet.jpa.JpaExecutor;
import org.aptech.t2109e.jspservlet.utils.delete.DeleteQueryBuilder;
import org.aptech.t2109e.jspservlet.utils.insert.InsertQueryBuilder;
import org.aptech.t2109e.jspservlet.utils.select.SelectQueryBuilder;
import org.aptech.t2109e.jspservlet.utils.update.UpdateQueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class JpaExecutorImpl<T> implements JpaExecutor<T> {
    private Class<T> clazz;
    private String className;
    private String tableName;

    public JpaExecutorImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.className = clazz.getSimpleName();
        this.tableName = (clazz.getAnnotation(Entity.class) != null) ? clazz.getAnnotation(Entity.class).tableName().toLowerCase()
                : this.className.toLowerCase();
    }

    @Override
    public List<T> entityParser(ResultSet rs) {
        List<T> entitys = new ArrayList<>();
        try {
            while (rs.next()) {
                T entity = clazz.getDeclaredConstructor().newInstance();
                System.err.println(entity.getClass().getDeclaredFields());
                for (Field f : entity.getClass().getDeclaredFields()) {
                    String columnName;
                    if (f.isAnnotationPresent(Column.class) && !StringUtils.isEmpty(f.getAnnotation(Column.class).name())) {
                        Column columnInfo = f.getAnnotation(Column.class);
                        columnName = columnInfo.name();
                        f.setAccessible(true);
                        switch (columnInfo.dataType()) {
                            case INTEGER:
                                f.set(entity, rs.getInt(columnName));
                                break;
                            case TEXT:
                                f.set(entity, rs.getString(columnName));
                                break;
                            case BIG_INTEGER:
                                f.set(entity, rs.getInt(columnName));
                                break;
                            case SMALL_INTEGER:
                                f.set(entity, rs.getInt(columnName));
                                break;
                            case DATE:
                                f.set(entity, rs.getString(columnName));
                                break;
                            case FLOAT:
                                f.set(entity, rs.getFloat(columnName));
                                break;
                            case DOUBLE:
                                f.set(entity, rs.getInt(columnName));
                                break;
                        }
                    }
                    if (f.isAnnotationPresent(Id.class) && !StringUtils.isEmpty(f.getAnnotation(Id.class).name())) {
                        Id id = f.getAnnotation(Id.class);
                        f.setAccessible(true);
                        f.set(entity, rs.getInt(id.name()));
                    }
                }
                entitys.add(entity);
            }
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return entitys;
    }

    @Override
    public List<T> findAll() {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        queryBuilder.buildBaseSelectQuery(tableName);

        String query = queryBuilder.getQuery();

        System.err.println(query);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            return entityParser(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public T findById(int id) {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        queryBuilder.buildBaseSelectQuery(tableName);

        String idColumnName = null;
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                idColumnName = f.getAnnotation(Id.class).name();
            }
        }
        queryBuilder.and(idColumnName + " = ?");
        queryBuilder.setLimitClause(1);
        String query = queryBuilder.getQuery();
        System.err.println(query);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setObject(1, id);

            System.err.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> list = entityParser(resultSet);
            if (!list.isEmpty()) {
                return list.get(0);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public T findByField(String fieldName, String value) {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        queryBuilder.buildBaseSelectQuery(tableName);

        queryBuilder.and(fieldName + " = ?");
        queryBuilder.setLimitClause(1);

        String query = queryBuilder.getQuery();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setObject(1, value);

            System.err.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> tList = entityParser(resultSet);

            if(!tList.isEmpty()){
                return tList.get(0);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<T> findAllByField(String fieldName, String value) {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        queryBuilder.buildBaseSelectQuery(tableName);

        queryBuilder.and(fieldName + " = ?");
        String query = queryBuilder.getQuery();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setObject(1, value);

            System.err.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            return entityParser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public T create(T entity) {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(tableName);
        Map<String, Object> columnValues = new HashMap<>();

        try {
            for (Field f : entity.getClass().getDeclaredFields()) {
                if (f.isAnnotationPresent(Column.class) && !StringUtils.isEmpty(f.getAnnotation(Column.class).name())) {
                    f.setAccessible(true);
                    Column columnInfo = f.getAnnotation(Column.class);
                    String columnName = columnInfo.name();
                    Object value = f.get(entity);
                    columnValues.put(columnName, value);
                }
            }

            insertQueryBuilder.addRow(columnValues);

            String query = insertQueryBuilder.getQuery();
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            List<Object> values = new ArrayList<>(columnValues.values());
            int parameterIndex = 1;
            for (Object value : values) {
                preparedStatement.setObject(parameterIndex++, value);
            }

            System.err.println(preparedStatement);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);

                    for (Field f : entity.getClass().getDeclaredFields()) {
                        f.setAccessible(true);
                        if (f.isAnnotationPresent(Id.class)) {
                            f.set(entity, generatedId);
                            break;
                        }
                    }

                    return entity;
                }
            }

            return null;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<T> createMany(List<T> listEntity) {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(tableName);

        try {
            for (T entity : listEntity) {
                Map<String, Object> columnValues = new HashMap<>();

                for (Field f : entity.getClass().getDeclaredFields()) {
                    if (f.isAnnotationPresent(Column.class) && !StringUtils.isEmpty(f.getAnnotation(Column.class).name())) {
                        f.setAccessible(true);
                        Column columnInfo = f.getAnnotation(Column.class);
                        String columnName = columnInfo.name();
                        Object value = f.get(entity);
                        columnValues.put(columnName, value);
                    }
                }
                insertQueryBuilder.addRow(columnValues);
            }
            List<Map<String, Object>> rows = insertQueryBuilder.getRows();

            String query = insertQueryBuilder.getQuery();
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int parameterIndex = 1;
            for (Map<String, Object> columnValues : rows) {
                for (Object value : columnValues.values()) {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }
            System.err.println(preparedStatement);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                List<T> insertedEntities = new ArrayList<>();

                int entityIndex = 0;
                while (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    T entity = listEntity.get(entityIndex);

                    for (Field f : entity.getClass().getDeclaredFields()) {
                        f.setAccessible(true);
                        if (f.isAnnotationPresent(Id.class)) {
                            f.set(entity, generatedId);
                            break;
                        }
                    }

                    insertedEntities.add(entity);
                    entityIndex++;
                }

                return insertedEntities;
            }

            return Collections.emptyList();
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean deleteById(int id) {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(tableName);

        String idColumnName = null;
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                idColumnName = f.getAnnotation(Id.class).name();
            }
        }

        deleteQueryBuilder.andClause(idColumnName + " = ?");

        String query = deleteQueryBuilder.getQuery();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setObject(1, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public T updateById(int id, T entity) {
        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder(tableName);
        String idColumnName = null;
        try {
            for (Field f : entity.getClass().getDeclaredFields()) {
                String columnName;
                if (f.isAnnotationPresent(Column.class) && !StringUtils.isEmpty(f.getAnnotation(Column.class).name())) {
                    f.setAccessible(true);
                    Column columnInfo = f.getAnnotation(Column.class);
                    columnName = columnInfo.name();
                    Object value = f.get(entity);
                    updateQueryBuilder.addColumnValue(columnName, value);
                }
                if (f.isAnnotationPresent(Id.class)) {
                    idColumnName = f.getAnnotation(Id.class).name();
                }
            }

            updateQueryBuilder.andClause(idColumnName + " = " + id);

            String query = updateQueryBuilder.getQuery();

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            List<Object> params = updateQueryBuilder._getParams();

            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            System.err.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return entity;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Pagination<T> filterAndPagination(Pagination<T> pagination){
        pagination.buildBaseSelectQuery(tableName);

        Connection conn;
        try {
            conn = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (conn == null) {
            System.err.println("Connection is null");
        }

        String query = pagination.getQuery();
        String countQuery = pagination.getQueryCount();

        System.err.println(countQuery);
        System.err.println(query);

        int totalItems = 0;
        List<T> tList;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            tList = entityParser(rs);

            preparedStatement = conn.prepareStatement(countQuery);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                totalItems = rs.getInt(1);
            }

            pagination.build(totalItems,tList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return pagination;
    }
}
