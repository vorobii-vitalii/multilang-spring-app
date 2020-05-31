package com.multilang.app.Multilangapp.validation;

import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LanguageCodeValidator implements ConstraintValidator<LanguageCode, String> {

    private final LocateService locateService;

    @Autowired
    public LanguageCodeValidator( LocateService locateService ) {
        this.locateService = locateService;
    }

    @Override
    public void initialize(LanguageCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String langCode, ConstraintValidatorContext context) {
        return locateService.existsByLanguageCode(langCode);
    }

}
