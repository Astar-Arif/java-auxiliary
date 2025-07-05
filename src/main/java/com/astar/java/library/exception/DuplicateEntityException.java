package com.astar.java.library.exception;

//TODO ENHANCE
public class DuplicateEntityException extends AbstractRuntimeException {
    public DuplicateEntityException(String id, String message, String code) {
        super(id, message, code);
    }
}
