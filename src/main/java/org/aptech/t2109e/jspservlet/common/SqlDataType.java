package org.aptech.t2109e.jspservlet.common;

public enum SqlDataType {
    INTEGER("INT"),
    SMALL_INTEGER("SMALLINT"),
    BIG_INTEGER("BIGINT"),
    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    TIME_STAMP("TIMESTAMP"),
    TEXT("TEXT");
    public String value ;
    private SqlDataType(String value) {
        this.value = value;
    }

}
