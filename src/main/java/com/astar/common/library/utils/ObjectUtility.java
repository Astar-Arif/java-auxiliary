package com.astar.common.library.utils;

import jakarta.validation.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;
import java.util.Set;

//TODO ADD MORE
public class ObjectUtility extends ObjectUtils {

    private static final ValidatorFactory VALIDATION_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATION_FACTORY.getValidator();


    public static <T> void validateObject(T object) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
    }

    public static boolean isMatch(Object value, Object... target) {
        for (Object ele : target) {
            if (Objects.equals(value, ele)) return true;
        }
        return false;
    }
}
