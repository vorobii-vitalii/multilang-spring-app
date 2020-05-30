package com.multilang.app.Multilangapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LanguageCodeValidator.class)
public @interface LanguageCode {

    String message() default "Language code is wrong";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
