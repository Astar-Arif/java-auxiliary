package com.astar.java.library.exception;


import java.util.Collection;


public class ErrorResponse {

    private String exceptionId;
    private String error;
    private String message;
    private Integer status;
    private Collection<ErrorField> errorFields;

    public ErrorResponse() {
    }


}
