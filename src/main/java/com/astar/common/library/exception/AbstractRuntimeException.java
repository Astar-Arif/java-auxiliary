package com.astar.common.library.exception;

public abstract class AbstractRuntimeException extends RuntimeException {

    private String id;
    private String code;

    public AbstractRuntimeException(String id, String message, String code) {
        super(message);
        this.id = id;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    private String getCode() {
        return this.code;
    }
}
