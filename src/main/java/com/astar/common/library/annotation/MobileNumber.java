package com.astar.common.library.annotation;

import com.astar.common.library.pojo.MobileNumberValidator;
import com.astar.common.library.pojo.NoEmojiValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {MobileNumberValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileNumber {
}
