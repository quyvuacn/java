package org.aptech.t2109e.jspservlet.common;

public enum SqlState {
    CREATE_TABLE ("CREATE TABLE"),
    SPACE(" "),
    MULTIPLE_SPACE("       "),
    OPEN_PARENTHESES("("),
    CLOSE_PARENTHESES(")"),
    PRIMARY_KEY ("PRIMARY KEY"),
    AUTO_INCREMENT("AUTO_INCREMENT"),
    COMMA (","),
    INSERT_INTO ("INSERT INTO"),
    VALUES  ("VALUES"),
    APOSTROPHE  ("'"),
    NULL  ("null"),
    SELECT_ASTERISK  ("SELECT *"),
    FROM  ("FROM"),
    WHERE ( "WHERE"),
    EQUAL  ("="),
    UPDATE  ("UPDATE"),
    SET  ("SET"),
    DELETE  ("DELETE"),
    LIMIT("LIMIT"),
    OFFSET("OFFSET"),
    DOWN_LINE("\n"),

    FOREIGN_KEY ("FOREIGN KEY"),
    REFERENCES  ("REFERENCES"),
    DROP  ("DROP"),
    DROP_TABLE ("DROP TABLE"),
    IF_EXISTS("IF EXISTS"),
    LIKE("LIKE"),
    GREATER_THAN(">"),
    LESS_THAN( "<"),
    GREATER_THAN_OR_EQUAL_TO( ">="),
    LESS_THAN_OR_EQUAL_TO( "<="),
    NOT_EQUAL_TO ("!="),
    QUEST ("?");

    public String value ;
    private SqlState(String value) {
        this.value = value;
    }
}
