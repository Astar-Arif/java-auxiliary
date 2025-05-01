package com.astar.common.library.annotation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import com.astar.common.library.pojo.NoEmojiValidator;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = {NoEmojiValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface  NoEmoji {
    String message() default "Emojis are not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
