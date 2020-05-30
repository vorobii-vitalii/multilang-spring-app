package com.multilang.app.Multilangapp.validation;

import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TranslatedTextValidator implements ConstraintValidator<TranslatedText, Long> {

    private final TranslatedTextRepository translatedTextRepository;

    @Autowired
    public TranslatedTextValidator(TranslatedTextRepository translatedTextRepository) {
        this.translatedTextRepository = translatedTextRepository;
    }

    @Override
    public void initialize(TranslatedText constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long translatedTextId, ConstraintValidatorContext context) {
        return translatedTextRepository.existsById(translatedTextId);
    }

}
