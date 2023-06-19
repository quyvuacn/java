package org.aptech.t2109e.jspservlet.utils.select;

public class OrderClauseBuilder {

    private StringBuilder whereClause;

    public OrderClauseBuilder() {
        this.whereClause = new StringBuilder();
    }

    public void add(String columnName, boolean desc) {
        if (!isEmpty()) {
            this.whereClause.append(", ");
        }

        this.whereClause.append(columnName);

        if (desc) {
            this.whereClause.append(" DESC");
        }
    }

    public boolean isEmpty() {
        return this.whereClause.toString().isEmpty();
    }

    public String getClause() {
        return this.whereClause.toString();
    }

}