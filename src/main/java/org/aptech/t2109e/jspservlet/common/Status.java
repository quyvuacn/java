package org.aptech.t2109e.jspservlet.common;

public enum Status {
    ACTIVE(1),
    INACTIVE(0);
    public int value ;
    private Status(int value) {
        this.value = value;
    }
}
