package org.aptech.t2109e.jspservlet.utils.delete;

import org.aptech.t2109e.jspservlet.utils.WhereClauseBuilder;

public class DeleteQueryBuilder {
    private String tableName;
    private WhereClauseBuilder whereClauseBuilder;

    public DeleteQueryBuilder(String tableName) {
        this.tableName = tableName;
        this.whereClauseBuilder = new WhereClauseBuilder();
    }

    public void andClause(String clause){
        this.whereClauseBuilder.andClause(clause);
    }

    public void orClause(String clause){
        this.whereClauseBuilder.orClause(clause);
    }


    public String getQuery() {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(tableName);

        if (!whereClauseBuilder.isEmpty()) {
            query.append(String.format(" WHERE %s", whereClauseBuilder.getClause()));
        }
        return query.toString();
    }
}
