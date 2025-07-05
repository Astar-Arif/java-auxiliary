package com.astar.java.library.exception;


public class ErrorField {

    private String field;
    private String message;
    private String code;

    public ErrorField(String field, String message, String code) {
        this.field = field;
        this.message = message;
        this.code = code;
    }
}
