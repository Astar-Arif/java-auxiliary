package com.astar.common.library.exception;

public abstract class AbstractException extends Exception {

    private String id;
    private String code;

    public AbstractException(String id, String message, String code) {
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
