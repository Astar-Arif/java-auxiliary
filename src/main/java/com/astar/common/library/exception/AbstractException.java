package com.astar.common.library.exception;

public abstract class AbstractException extends Exception {

    private String field;
    private String code;

    public AbstractException(String message, String code, String field) {
        super(message);
        this.code = code;
        this.field = field;
    }

    public ErrorField toErrorField() {
        return new ErrorField(this.getField(), this.getMessage(), this.getCode());

    }

    private String getCode() {
        return this.code;
    }

    private String getField() {
        return this.field;
    }


}
