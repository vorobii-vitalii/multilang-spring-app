package com.multilang.app.Multilangapp.validation;

import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.service.TranslatedTextService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TranslatedTextValidator implements ConstraintValidator<TranslatedText, Long> {

    private final TranslatedTextService translatedTextService;

    @Autowired
    public TranslatedTextValidator(TranslatedTextService translatedTextService) {
        this.translatedTextService = translatedTextService;
    }

    @Override
    public void initialize(TranslatedText constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long translatedTextId, ConstraintValidatorContext context) {
        return translatedTextService.existsById(translatedTextId);
    }

}
