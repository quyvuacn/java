package org.aptech.t2109e.jspservlet.utils.update;

import org.aptech.t2109e.jspservlet.utils.ParamHolder;
import org.aptech.t2109e.jspservlet.utils.WhereClauseBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateQueryBuilder {
    private String tableName;
    private WhereClauseBuilder whereClauseBuilder;
    private ParamHolder paramHolder;
    private Map<String,Object> columnValueMap;

    public UpdateQueryBuilder(String tableName) {
        this.tableName = tableName;
        this.whereClauseBuilder = new WhereClauseBuilder();
        this.paramHolder = new ParamHolder();
        this.columnValueMap = new HashMap<>();
    }

    public void addColumnValue(String column, Object value) {
        columnValueMap.put(column, value);
    }

    public void addColumnValues(Map<String, Object> columnValues) {
        columnValueMap.putAll(columnValues);
    }

    public void andClause(String clause) {
        this.whereClauseBuilder.andClause(clause);
    }

    public void orClause(String clause) {
        this.whereClauseBuilder.orClause(clause);
    }

    public void addParams(Object param) {
        this.paramHolder.add(param);
    }

    public void addParams(Collection<? extends Object> params) {
        paramHolder.addAll(params);
    }

    public List<Object> _getParams() {
        return paramHolder._getParams();
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(tableName).append(" SET ");

        int numColumns = columnValueMap.size();
        int columnCount = 0;


        for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
            query.append(entry.getKey()).append(" = ?");
            columnCount++;
            if (columnCount < numColumns) {
                query.append(", ");
            }
            paramHolder.add(entry.getValue());
        }

        if (!whereClauseBuilder.isEmpty()) {
            query.append(String.format(" WHERE %s", whereClauseBuilder.getClause()));
        }



        return query.toString();
    }

    private String getFormattedValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value.toString();
    }


}
