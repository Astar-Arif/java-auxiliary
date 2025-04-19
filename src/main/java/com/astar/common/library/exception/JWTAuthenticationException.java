package com.astar.common.library.exception;

public class JWTAuthenticationException extends AbstractRuntimeException {
    public JWTAuthenticationException(String message, String code, String field) {
        super(message, code, field);
    }


}
