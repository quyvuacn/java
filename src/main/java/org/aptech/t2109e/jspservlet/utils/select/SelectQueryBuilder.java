package org.aptech.t2109e.jspservlet.utils.select;

import lombok.Setter;
import org.aptech.t2109e.jspservlet.utils.WhereClauseBuilder;
import org.aptech.t2109e.jspservlet.common.SqlState;
import org.aptech.t2109e.jspservlet.utils.ParamHolder;

import java.util.Collection;
import java.util.List;


@Setter
public class SelectQueryBuilder {
    private String selectClause;
    private String fromClause;
    private WhereClauseBuilder whereClauseBuilder;
    private ParamHolder paramHolder;
    private StringBuilder havingClauseBuilder;
    private OrderClauseBuilder orderClauseBuilder;
    private StringBuilder groupByClauseBuilder;
    private int limitClause;
    private int offsetClause;


    public SelectQueryBuilder() {
        paramHolder = new ParamHolder();
        whereClauseBuilder = new WhereClauseBuilder();
        havingClauseBuilder = new StringBuilder();
        orderClauseBuilder = new OrderClauseBuilder();
        groupByClauseBuilder = new StringBuilder();
        limitClause = 0;
        offsetClause = 0;
    }

    public void buildBaseSelectQuery(String selectClause, String fromClause) {
        this.selectClause = selectClause;
        this.fromClause = fromClause;
    }

    public void buildBaseSelectQuery(String fromClause) {
        this.selectClause = "*";
        this.fromClause = fromClause;
    }


    public void setSelectClause(String selectClause) {
        this.selectClause = selectClause;
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

    public SelectQueryBuilder and(String clause) {
        this.whereClauseBuilder.andClause(clause);
        return this;
    }

    public SelectQueryBuilder or(String clause) {
        this.whereClauseBuilder.orClause(clause);
        return this;
    }

    public SelectQueryBuilder orderBy(String fieldName) {
        this.orderClauseBuilder.add(fieldName, false);
        return this;
    }

    public SelectQueryBuilder orderByDesc(String fieldName) {
        this.orderClauseBuilder.add(fieldName, true);
        return this;
    }

    public SelectQueryBuilder offset(int offset) {
        this.offsetClause = offset;
        return this;
    }

    public SelectQueryBuilder limit(int limit) {
        this.limitClause = limit;
        return this;
    }

    public int getLimit() {
        return this.limitClause;
    }

    public SelectQueryBuilder where(String fieldName, String value) {
        StringBuilder clause = new StringBuilder();
        clause.append(fieldName).append(" = ").append(handleType(value));

        this.and(clause.toString());

        return this;
    }

    public SelectQueryBuilder where(String fieldName, String operator, String value) {
        StringBuilder clause = new StringBuilder();
        clause.append(fieldName).append(SqlState.SPACE.value).append(operator).append(SqlState.SPACE.value).append(handleType(value));

        this.and(clause.toString());

        return this;
    }

    public String getQueryCount(){
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        queryBuilder.buildBaseSelectQuery("COUNT(*)",this.fromClause);
        queryBuilder.whereClauseBuilder = this.whereClauseBuilder;
        return queryBuilder.getQuery();
    }

    public String handleType(String value) {
        if (value == null) return "NULL";

        if (value instanceof String) {
            value = "'" + value + "'";
        }

        return value;
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();
        query.append(String.format(" SELECT %s", selectClause));
        query.append(String.format(" FROM %s", fromClause));

        if (!whereClauseBuilder.isEmpty()) {
            query.append(String.format(" WHERE %s", whereClauseBuilder.getClause()));
        }

        if (groupByClauseBuilder.length() > 0) {
            query.append(String.format(" GROUP BY %s", groupByClauseBuilder));
        }

        if (havingClauseBuilder.length() > 0) {
            query.append(String.format(" HAVING %s", havingClauseBuilder));
        }

        if (!orderClauseBuilder.isEmpty()) {
            query.append(String.format(" ORDER BY %s", orderClauseBuilder.getClause()));
        }

        if (limitClause > 0) {
            query.append(String.format(" LIMIT %s", limitClause));
        }

        if (offsetClause > 0) {
            query.append(String.format(" OFFSET %s", offsetClause));
        }

        return query.toString();
    }

}
