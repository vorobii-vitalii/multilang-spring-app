package com.multilang.app.Multilangapp.validation;

import com.multilang.app.Multilangapp.repository.LocateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LanguageCodeValidator implements ConstraintValidator<LanguageCode, String> {

    private final LocateRepository locateRepository;

    @Autowired
    public LanguageCodeValidator(LocateRepository locateRepository) {
        this.locateRepository = locateRepository;
    }

    @Override
    public void initialize(LanguageCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String langCode, ConstraintValidatorContext context) {
        return locateRepository.existsByLanguageCode(langCode);
    }

}
