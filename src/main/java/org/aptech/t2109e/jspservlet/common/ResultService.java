package org.aptech.t2109e.jspservlet.common;

public class ResultService<T> {
    public ResultService(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResultService(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    public static ResultService OK = new ResultService(StatusCode.OK, "Success!");

    public static <T> ResultService<T> OK(T data) {
        return new ResultService<T>(StatusCode.OK, "Success!", data);
    }

    public int statusCode;
    public String message;
    public T data;
}
