package com.astar.common.library.annotation;

import com.astar.common.library.pojo.MobileNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {MobileNumberValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileNumber {
    String message() default "Invalid Mobile Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
