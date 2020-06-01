package com.multilang.app.Multilangapp;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.entity.Translation;
import com.multilang.app.Multilangapp.exceptions.LocateNotFoundException;
import com.multilang.app.Multilangapp.exceptions.TranslatedTextNotFoundException;
import com.multilang.app.Multilangapp.exceptions.TranslationNotFoundException;
import com.multilang.app.Multilangapp.mapper.LocateMapper;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.mapper.TranslatedTextMapper;
import com.multilang.app.Multilangapp.mapper.TranslationMapper;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.repository.TranslationRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import com.multilang.app.Multilangapp.service.TranslatedTextService;
import com.multilang.app.Multilangapp.service.TranslationService;
import com.multilang.app.Multilangapp.service.impl.LocateServiceImpl;
import com.multilang.app.Multilangapp.service.impl.TranslatedTextServiceImpl;
import com.multilang.app.Multilangapp.service.impl.TranslationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TranslationServiceTest {

    @Mock
    private TranslationRepository translationRepository;

    @Mock
    private LocateRepository locateRepository;

    @Mock
    private TranslatedTextRepository translatedTextRepository;

    private Mapper<Translation, TranslationDTO> translationDTOMapper;

    private TranslationService translationService;

    private LocateService locateService;

    private Mapper<Locate, LocateDTO> locateDTOMapper;

    private TranslatedTextService translatedTextService;

    private Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        locateDTOMapper = new LocateMapper();
        locateService = new LocateServiceImpl(locateRepository, locateDTOMapper);
        translatedTextDTOMapper = new TranslatedTextMapper();
        translatedTextService = new TranslatedTextServiceImpl(translatedTextRepository, translatedTextDTOMapper);
        translationDTOMapper = new TranslationMapper(translatedTextService,locateService, locateDTOMapper, translatedTextDTOMapper );
        translationService = new TranslationServiceImpl(translationRepository, translationDTOMapper, translatedTextRepository, locateRepository);
    }

    @Test
    public void testGetById_returnsObject() {

        // Input data

        final Long id = 1L;
        final String text = "Hello world";
        final String langCode = "ua";
        final Long translatedTextId = 5L;

        // Construct the Translation object

        Translation translation = new Translation();
        translation.setId(id);
        translation.setText(text);
        translation.setLocate(new Locate(2L, langCode));
        translation.setTranslatedText(new TranslatedText(translatedTextId));

        // Assume that Translation exists by given id

        doReturn(Optional.of(translation)).when(translationRepository).findById(id);

        // Verify

        TranslationDTO translationDTO = translationService.getById(id);

        assertEquals(translationDTO.getId(), id);
        assertEquals(translationDTO.getText(), text);
        assertEquals(translationDTO.getLanguageCode(), langCode);
        assertEquals(translationDTO.getTranslatedTextId(), translatedTextId);
    }



    @Test(expected = TranslationNotFoundException.class)
    public void testGetByWrongId_throwsTheException() {

        // Input data

        final Long id = 2L;

        // Assume that there is no translation by provided id

        doReturn(Optional.empty()).when(translationRepository).findById(id);

        // Verify

        translationService.getById(id);
    }

    @Test
    public void testGetByTranslatedTextAndLocate_returnsCorrectObject() {

        final Long translatedTextId = 2L;
        final String langCode = "ua";
        final Long id = 2L;
        final String text = "Some text";

        TranslatedText translatedText = new TranslatedText(translatedTextId);
        Locate locate = new Locate(2L, langCode);

        Translation translation = new Translation();
        translation.setId(id);
        translation.setTranslatedText(translatedText);
        translation.setLocate(locate);
        translation.setText(text);

        doReturn(Optional.of(translatedText)).when(translatedTextRepository).findById(translatedTextId);
        doReturn(Optional.of(locate)).when(locateRepository).findByLanguageCode(langCode);
        doReturn(Optional.of(translation)).when(translationRepository).findByTranslatedTextAndLocate(translatedText, locate);

        TranslationDTO translationDTO = translationService.getByTranslatedTextAndLocate(translatedTextId, langCode);

        assertEquals(translationDTO.getId(), id);
        assertEquals(translationDTO.getText(), text);
        assertEquals(translationDTO.getTranslatedTextId(), translatedTextId);
        assertEquals(translationDTO.getLanguageCode(), langCode);
    }

    @Test(expected = TranslatedTextNotFoundException.class)
    public void testGetByWrongTranslatedTextAndLocate_throwsTheException() {

        final Long translatedTextId = 2L;
        final String langCode = "ua";

        Locate locate = new Locate(2L, langCode);

        doReturn(Optional.empty()).when(translatedTextRepository).findById(translatedTextId);
        doReturn(Optional.of(locate)).when(locateRepository).findByLanguageCode(langCode);

        translationService.getByTranslatedTextAndLocate(translatedTextId, langCode);
    }

    @Test(expected = LocateNotFoundException.class)
    public void testGetByTranslatedTextAndWrongLocate_throwsTheException() {

        final Long translatedTextId = 2L;
        final String langCode = "ua";

        TranslatedText translatedText = new TranslatedText(translatedTextId);

        doReturn(Optional.of(translatedText)).when(translatedTextRepository).findById(translatedTextId);
        doReturn(Optional.empty()).when(locateRepository).findByLanguageCode(langCode);

        translationService.getByTranslatedTextAndLocate(translatedTextId, langCode);
    }

    @Test(expected = TranslationNotFoundException.class)
    public void testAbsentGetByTranslatedTextAndLocate_throwsTheException() {

        final Long translatedTextId = 2L;
        final String langCode = "ua";

        TranslatedText translatedText = new TranslatedText(translatedTextId);
        Locate locate = new Locate(2L, langCode);

        doReturn(Optional.of(translatedText)).when(translatedTextRepository).findById(translatedTextId);
        doReturn(Optional.of(locate)).when(locateRepository).findByLanguageCode(langCode);
        doReturn(Optional.empty()).when(translationRepository).findByTranslatedTextAndLocate(translatedText, locate);

        translationService.getByTranslatedTextAndLocate(translatedTextId, langCode);
    }


    @Test
    public void testGetAllByParentTranslatedTextId_returnsList() {

        // Input data

        final Long translatedTextId = 2L;

        // Construct TranslatedText object

        TranslatedText translatedText = new TranslatedText();
        translatedText.setId(translatedTextId);

        Translation translation1 = new Translation(
                        1L,
                        "якийсь текст....",
                        new Locate(2L, "ua"),
                        translatedText );
        Translation translation2 = new Translation(
                        2L,
                        "какой-то текст...",
                        new Locate(3L, "ru"),
                        translatedText );
        Translation translation3 = new Translation(
                        3L,
                        "some text",
                        new Locate(1L, "en"),
                        translatedText );

        Set<Translation> translationSet = new HashSet<>(Arrays.asList(translation1, translation2, translation3));
        translatedText.setTranslations(translationSet);

        doReturn(Optional.of(translatedText)).when(translatedTextRepository).findById(translatedTextId);

        // Verify

        List<TranslationDTO> translationDTOList = translationService.getAllByParentTranslatedText(translatedTextId);

        assertEquals(translationDTOList, translationSet.stream().map(translationDTOMapper::to).collect(Collectors.toList()));
    }

    @Test(expected = TranslatedTextNotFoundException.class)
    public void testGetAllByWrongParentTranslatedTextId_throwsTheException() {

        // Input data

        final Long translatedTextId = 2L;

        doReturn(Optional.empty()).when(translatedTextRepository).findById(translatedTextId);

        // Verify

        translationService.getAllByParentTranslatedText(translatedTextId);
    }

    @Test
    public void testAdd_returnsCorrectObject() {

        // Input data

        final Long id = 1L;
        final String text = "Hello world";
        final String langCode = "ua";
        final Long translatedTextId = 5L;

        // Construct the Translation object

        Locate locate = new Locate(2L, langCode);
        TranslatedText translatedText = new TranslatedText(translatedTextId);

        Translation translation = new Translation();
        translation.setId(id);
        translation.setText(text);
        translation.setLocate(locate);
        translation.setTranslatedText(translatedText);

        // Construct the TranslationDTO object

        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setId(id);
        translationDTO.setText(text);
        translationDTO.setLanguageCode(langCode);
        translationDTO.setTranslatedTextId(translatedTextId);

        doReturn(translation).when(translationRepository).save(translation);
        doReturn(Optional.of(locate)).when(locateRepository).findByLanguageCode(langCode);
        doReturn(Optional.of(translatedText)).when(translatedTextRepository).findById(translatedTextId);

        // Verify

        TranslationDTO savedTranslation = translationService.add(translationDTO);

        assertEquals(translationDTO, savedTranslation);
    }

    @Test
    public void testDeleteByCorrectId_performsDelete() {

        // Input data

        final Long id = 2L;

        doReturn(true).when(translationRepository).existsById(id);

        // Verify

        translationService.deleteById(id);
    }

    @Test(expected = TranslationNotFoundException.class)
    public void testDeleteByWrongId_throwsTheException() {

        // Input data

        final Long id = 2L;

        doReturn(false).when(translationRepository).existsById(id);

        // Verify

        translationService.deleteById(id);
    }

}
