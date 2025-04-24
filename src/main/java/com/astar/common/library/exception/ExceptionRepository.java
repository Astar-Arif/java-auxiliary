package com.astar.common.library.exception;

public enum ExceptionRepository {

    JWT_AUTHENTICATION_GENERIC_EXCEPTION(1, "AU001", "JWT Exception Occurred", null),
    JWT_PARSE_EXCEPTION(2, "AU002", "JWT Parsing Fail", null);


    int exceptionId;
    String code;
    String message;
    String field;

    ExceptionRepository(int exceptionId, String code, String message, String field) {
        this.exceptionId = exceptionId;
        this.code = code;
        this.message = message;
        this.field = field;
    }
}
