package com.astar.common.library.exception;

public class JWTAuthenticationException extends AbstractRuntimeException {
    public JWTAuthenticationException(String id, String message, String code) {
        super(id ,message, code);
    }


}
