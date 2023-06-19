package org.aptech.t2109e.jspservlet.utils;

public class WhereClauseBuilder {
    private StringBuilder whereClause;

    public WhereClauseBuilder() {
        super();
        this.whereClause = new StringBuilder();
    }

    public WhereClauseBuilder andClause(String clause) {
        if (!isEmpty()) {
            this.whereClause.append(" AND ");
        }
        this.whereClause.append(clause);
        return this;
    }

    public WhereClauseBuilder orClause(String clause) {
        if (!isEmpty()) {
            this.whereClause.append(" OR ");
        }
        this.whereClause.append(clause);
        return this;
    }

    public boolean isEmpty() {
        return this.whereClause.toString().isEmpty();
    }


    public String toString() {
        return this.whereClause.toString();
    }

    public String getClause() {
        return this.whereClause.toString();
    }
}
