package com.multilang.app.Multilangapp;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.mapper.LocateMapper;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.mapper.TranslatedTextMapper;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import com.multilang.app.Multilangapp.service.TranslatedTextService;
import com.multilang.app.Multilangapp.service.impl.LocateServiceImpl;
import com.multilang.app.Multilangapp.service.impl.TranslatedTextServiceImpl;
import com.multilang.app.Multilangapp.validation.LanguageCodeValidator;
import com.multilang.app.Multilangapp.validation.TranslatedTextValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class TranslationValidationTest  {

    @Mock
    private TranslatedTextRepository translatedTextRepository;

    @Mock
    private LocateRepository locateRepository;

    private LocateService locateService;

    private TranslatedTextService translatedTextService;

    private LanguageCodeValidator languageCodeValidator;

    private TranslatedTextValidator translatedTextValidator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mapper<Locate, LocateDTO> locateDTOMapper = new LocateMapper();
        Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper = new TranslatedTextMapper();
        locateService = new LocateServiceImpl(locateRepository, locateDTOMapper);
        translatedTextService = new TranslatedTextServiceImpl(translatedTextRepository, translatedTextDTOMapper);
        languageCodeValidator = new LanguageCodeValidator(locateService);
        translatedTextValidator = new TranslatedTextValidator(translatedTextService);
    }

    @Test
    public void whenGivenCorrectTranslatedTextId_thenValidationPasses() {

        final Long translatedTextId = 2L;

        // Assume that TranslatedTextId exists by provided id

        doReturn(true).when(translatedTextRepository).existsById(translatedTextId);

        assertTrue(translatedTextValidator.isValid(translatedTextId, null));
    }

    @Test
    public void whenGivenCorrectLanguageCode_thenValidationPasses() {

        final String languageCode = "ua";

        // Assume that Locate exists by provided language code

        doReturn(true).when(locateRepository).existsByLanguageCode(languageCode);

        assertTrue(languageCodeValidator.isValid(languageCode, null));
    }

    @Test
    public void whenGivenWrongTranslatedTextId_thenValidationFails() {

        final Long translatedTextId = 2L;

        // Assume that TranslatedTextId doesn't exist by provided id

        doReturn(false).when(translatedTextRepository).existsById(translatedTextId);

        assertFalse(translatedTextValidator.isValid(translatedTextId, null));
    }

    @Test
    public void whenGivenWrongLanguageCode_thenValidationFails() {

        final String languageCode = "ua";

        // Assume that Locate doesnt exist by provided language code

        doReturn(false).when(locateRepository).existsByLanguageCode(languageCode);

        assertFalse(languageCodeValidator.isValid(languageCode, null));
    }


}
