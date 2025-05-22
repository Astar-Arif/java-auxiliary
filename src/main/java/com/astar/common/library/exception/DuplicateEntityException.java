package com.astar.common.library.exception;

//TODO ENHANCE
public class DuplicateEntityException extends AbstractRuntimeException {
    public DuplicateEntityException(String message, String code, String field) {
        super(message, code, field);
    }
}
