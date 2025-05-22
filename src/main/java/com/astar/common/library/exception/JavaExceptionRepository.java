package com.astar.common.library.exception;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public enum JavaExceptionRepository {


    JWT_AUTHENTICATION_GENERIC_EXCEPTION(1, "AU001", "JWT Exception Occurred", null),
    JWT_PARSE_EXCEPTION(2, "AU002", "JWT Parsing Fail", null);
    //!CACHE
    public static Map<Integer, JavaExceptionRepository> jExceptionRepoMap = new HashMap<>();

    static {
        LoggerFactory.getLogger("Anonymous").info("Caching JavaExceptionRepository");
        for (JavaExceptionRepository j : JavaExceptionRepository.values()) {
            jExceptionRepoMap.put(j.getExceptionId(), j);
        }
    }

    private int exceptionId;
    private String code;
    private String message;
    private String field;

    JavaExceptionRepository(int exceptionId, String code, String message, String field) {
        this.exceptionId = exceptionId;
        this.code = code;
        this.message = message;
        this.field = field;
    }

    public static void main(String[] args) {

    }

    public int getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s", exceptionId, code, message, field);
    }

    public JavaExceptionRepository[] getAll() {
        return JavaExceptionRepository.values();
    }


}


