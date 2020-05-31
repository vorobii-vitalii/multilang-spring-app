package com.multilang.app.Multilangapp;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.entity.Translation;
import com.multilang.app.Multilangapp.mapper.LocateMapper;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.mapper.TranslatedTextMapper;
import com.multilang.app.Multilangapp.mapper.TranslationMapper;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import com.multilang.app.Multilangapp.service.TranslatedTextService;
import com.multilang.app.Multilangapp.service.impl.LocateServiceImpl;
import com.multilang.app.Multilangapp.service.impl.TranslatedTextServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TranslationMapperTest {

    @Mock
    private TranslatedTextRepository translatedTextRepository;

    @Mock
    private LocateRepository locateRepository;

    private Mapper<Translation, TranslationDTO> translationDTOMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mapper<Locate, LocateDTO> locateDTOMapper = new LocateMapper();
        Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper = new TranslatedTextMapper();
        LocateService locateService = new LocateServiceImpl(locateRepository, locateDTOMapper);
        TranslatedTextService translatedTextService = new TranslatedTextServiceImpl(translatedTextRepository, translatedTextDTOMapper);
        translationDTOMapper = new TranslationMapper(translatedTextService, locateService, locateDTOMapper, translatedTextDTOMapper);
    }

    @Test
    public void testTranslationMapperFrom() {

        // Construct DTO object

        final Long id = 2L;
        final Long translationTextId = 1L;
        final String text = "Some text";
        final String languageCode = "ua";

        TranslationDTO translationDTO = new TranslationDTO(id, translationTextId, text, languageCode);

        // Assume that TranslationText exists by given id

        doReturn( Optional.of( new TranslatedText( translationTextId ) ) )
                .when(translatedTextRepository).findById(translationTextId);

        // Assume that Locate exists by given id

        doReturn(Optional.of(new Locate(1L, languageCode)))
                .when(locateRepository).findByLanguageCode(languageCode);

        Translation translation = translationDTOMapper.from(translationDTO);

        assertEquals(translation.getId(), translationDTO.getId());
        assertEquals(translation.getLocate().getLanguageCode(), translationDTO.getLanguageCode());
        assertEquals(translation.getText(), translationDTO.getText());
        assertEquals(translation.getTranslatedText().getId(), translationDTO.getTranslatedTextId());
    }

    @Test
    public void testTranslationMapperTo() {

        // Set-up the Translation object

        final Long id = 1L;
        final String text = "Hello world";
        final Long translatedTextId = 4L;
        final String langCode = "ua";
        Locate locate = new Locate(2L, langCode);
        TranslatedText translatedText = new TranslatedText(translatedTextId);

        Translation translation = new Translation();
        translation.setId(id);
        translation.setText(text);
        translation.setTranslatedText(translatedText);
        translation.setLocate(locate);

        // Perform test

        TranslationDTO translationDTO = translationDTOMapper.to(translation);
        assertEquals(translationDTO.getId(), translation.getId());
        assertEquals(translationDTO.getText(), translation.getText());
        assertEquals(translationDTO.getTranslatedTextId(), translation.getTranslatedText().getId());
        assertEquals(translationDTO.getLanguageCode(), translation.getLocate().getLanguageCode());
    }

}
