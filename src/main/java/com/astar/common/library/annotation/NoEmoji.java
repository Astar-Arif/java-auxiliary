package com.astar.common.library.annotation;

import com.astar.common.library.pojo.NoEmojiValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NoEmojiValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoEmoji {
    String message() default "Emojis are not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
