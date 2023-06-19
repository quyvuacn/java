package org.aptech.t2109e.jspservlet.utils.insert;

import java.util.*;

public class InsertQueryBuilder {
    private String tableName;

    private List<Map<String, Object>> rows;

    public InsertQueryBuilder(String tableName) {
        this.tableName = tableName;
        this.rows = new ArrayList<>();
    }

    public void addRow(Map<String, Object> columnValueMap) {
        rows.add(columnValueMap);
    }

    public List<Map<String, Object>> getRows(){
        return rows;
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(tableName);

        if (!rows.isEmpty()) {
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            Map<String, Object> firstRow = rows.get(0);
            for (String column : firstRow.keySet()) {
                columns.append(column).append(", ");
                values.append("?, ");
            }

            columns.setLength(columns.length() - 2); // Remove the trailing comma and space
            values.setLength(values.length() - 2); // Remove the trailing comma and space

            query.append(" (").append(columns).append(")");
            query.append(" VALUES ");

            for (int i = 0; i < rows.size(); i++) {
                query.append("(").append(values).append(")");
                if (i < rows.size() - 1) {
                    query.append(", ");
                }
            }
        }

        return query.toString();
    }
}
