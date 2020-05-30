package com.multilang.app.Multilangapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = TranslatedTextValidator.class)
public @interface TranslatedText {
    String message() default "Translated text is incorrect";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
