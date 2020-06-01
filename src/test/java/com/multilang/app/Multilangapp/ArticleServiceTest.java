package com.multilang.app.Multilangapp;

import com.multilang.app.Multilangapp.dto.ArticleDTO;
import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Article;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.entity.Translation;
import com.multilang.app.Multilangapp.exceptions.ArticleNotFoundException;
import com.multilang.app.Multilangapp.exceptions.LocateNotFoundException;
import com.multilang.app.Multilangapp.mapper.*;
import com.multilang.app.Multilangapp.repository.ArticleRepository;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.repository.TranslationRepository;
import com.multilang.app.Multilangapp.service.ArticleService;
import com.multilang.app.Multilangapp.service.LocateService;
import com.multilang.app.Multilangapp.service.TranslatedTextService;
import com.multilang.app.Multilangapp.service.TranslationService;
import com.multilang.app.Multilangapp.service.impl.ArticleServiceImpl;
import com.multilang.app.Multilangapp.service.impl.LocateServiceImpl;
import com.multilang.app.Multilangapp.service.impl.TranslatedTextServiceImpl;
import com.multilang.app.Multilangapp.service.impl.TranslationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private TranslatedTextRepository translatedTextRepository;

    @Mock
    private TranslationRepository translationRepository;

    @Mock
    private LocateRepository locateRepository;

    private ArticleService articleService;

    private TranslationService translationService;

    private TranslatedTextService translatedTextService;

    private LocateService locateService;

    private Mapper<Article, ArticleDTO> articleDTOMapper;

    private Mapper<Locate, LocateDTO> locateDTOMapper;

    private Mapper<Translation, TranslationDTO> translationDTOMapper;

    private Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        locateDTOMapper = new LocateMapper();
        locateService = new LocateServiceImpl(locateRepository, locateDTOMapper);
        translatedTextDTOMapper = new TranslatedTextMapper();
        translatedTextService = new TranslatedTextServiceImpl(translatedTextRepository, translatedTextDTOMapper);
        articleDTOMapper = new ArticleMapper(translatedTextRepository);
        translationDTOMapper = new TranslationMapper(translatedTextService, locateService, locateDTOMapper, translatedTextDTOMapper);
        translationService = new TranslationServiceImpl(translationRepository, translationDTOMapper, translatedTextRepository, locateRepository );
        articleDTOMapper = new ArticleMapper(translatedTextRepository);
        articleService = new ArticleServiceImpl(articleRepository, translationService, translatedTextRepository, articleDTOMapper );
    }

    @Test
    public void testGetById_returnsCorrectObject() {

        // Input data

        final Long id = 2L;
        final Date createdAt = new Date();

        // Construct the object with an aforementioned input data

        Article article = new Article();
        article.setId(id);
        article.setCreatedAt(createdAt);
        article.setTranslatedBody(new TranslatedText(2L));
        article.setTranslatedTitle(new TranslatedText(6L));

        doReturn(Optional.of(article)).when(articleRepository).findById(id);

        // Verify

        ArticleDTO articleDTO = articleService.getById(id);
        assertEquals(articleDTO.getId(), id);
        assertEquals(articleDTO.getCreatedAt(), createdAt);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testGetByIncorrectId_throwsTheException() {

        // Input data

        final Long id = 2L;

        doReturn(Optional.empty()).when(articleRepository).findById(id);

        // Verify

        articleService.getById(id);
    }

    @Test
    public void testAdd() {

        final Long id = 4L;
        final Long translatedTextId = 2L;
        final TranslatedText translatedText = new TranslatedText(translatedTextId);

        doReturn(translatedText).when(translatedTextRepository).save(new TranslatedText());

        Article article = new Article();
        article.setTranslatedTitle(translatedText);
        article.setTranslatedBody(translatedText);

        Article savedArticle = new Article();
        savedArticle.setId(id);
        savedArticle.setTranslatedTitle(translatedText);
        savedArticle.setTranslatedBody(translatedText);

        doReturn(savedArticle).when(articleRepository).save(article);

        // Verify

        ArticleDTO articleDTO = articleService.add();

        assertEquals(articleDTO.getId(), id);
        assertEquals(articleDTO.getTitleTranslatedTextId(), translatedTextId);
        assertEquals(articleDTO.getBodyTranslatedTextId(), translatedTextId);
    }

    @Test
    public void testGetByIdAndLanguageCode_returnsCorrectObject() {

        // Input data

        final Long id = 2L;
        final String langCode = "ua";
        final Long titleTranslatedTextId = 9L;
        final Long bodyTranslatedTextId = 9L;
        final Date createdAt = new Date();

        Locate locate = new Locate(7L, langCode);
        TranslatedText titleTranslatedText = new TranslatedText(titleTranslatedTextId);
        TranslatedText bodyTranslatedText = new TranslatedText(bodyTranslatedTextId);

        Article article = new Article();
        article.setId(id);
        article.setCreatedAt(createdAt);
        article.setTranslatedTitle(titleTranslatedText);
        article.setTranslatedBody(bodyTranslatedText);

        Translation translationTitle = new Translation();
        translationTitle.setId(2L);
        translationTitle.setText("Hello world");
        translationTitle.setLocate(locate);
        translationTitle.setTranslatedText(titleTranslatedText);

        Translation translationBody = new Translation();
        translationBody.setId(2L);
        translationBody.setText("Hello world");
        translationBody.setLocate(locate);
        translationBody.setTranslatedText(bodyTranslatedText);

        doReturn(Optional.of(article)).when(articleRepository).findById(id);
        doReturn(Optional.of(titleTranslatedText)).when(translatedTextRepository).findById(titleTranslatedTextId);
        doReturn(Optional.of(bodyTranslatedText)).when(translatedTextRepository).findById(bodyTranslatedTextId);
        doReturn(Optional.of(locate)).when(locateRepository).findByLanguageCode(langCode);
        doReturn(Optional.of(translationTitle)).when(translationRepository).findByTranslatedTextAndLocate(titleTranslatedText, locate);
        doReturn(Optional.of(translationBody)).when(translationRepository).findByTranslatedTextAndLocate(bodyTranslatedText, locate);

        // Verify

        ArticleDTO articleDTO = articleService.getByIdAndLanguageCode(id, langCode);

        assertEquals(articleDTO.getId(), id);
        assertEquals(articleDTO.getTitleTranslatedTextId(), titleTranslatedTextId);
        assertEquals(articleDTO.getBodyTranslatedTextId(), bodyTranslatedTextId);
        assertEquals(articleDTO.getCreatedAt(), createdAt);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testGetByWrongIdAndLanguageCode_throwsTheException() {

        // Input data

        final Long id = 2L;
        final String langCode = "ua";
        final Long titleTranslatedTextId = 9L;
        final Long bodyTranslatedTextId = 9L;
        final Date createdAt = new Date();

        TranslatedText titleTranslatedText = new TranslatedText(titleTranslatedTextId);
        TranslatedText bodyTranslatedText = new TranslatedText(bodyTranslatedTextId);

        Article article = new Article();
        article.setId(id);
        article.setCreatedAt(createdAt);
        article.setTranslatedTitle(titleTranslatedText);
        article.setTranslatedBody(bodyTranslatedText);


        doReturn(Optional.empty()).when(articleRepository).findById(id);

        // Verify

        articleService.getByIdAndLanguageCode(id, langCode);
    }


    @Test(expected = LocateNotFoundException.class)
    public void testGetByIdAndWrongLanguageCode_throwsTheException() {

        // Input data

        final Long id = 2L;
        final String langCode = "ua";
        final Long titleTranslatedTextId = 9L;
        final Long bodyTranslatedTextId = 9L;
        final Date createdAt = new Date();

        TranslatedText titleTranslatedText = new TranslatedText(titleTranslatedTextId);
        TranslatedText bodyTranslatedText = new TranslatedText(bodyTranslatedTextId);

        Article article = new Article();
        article.setId(id);
        article.setCreatedAt(createdAt);
        article.setTranslatedTitle(titleTranslatedText);
        article.setTranslatedBody(bodyTranslatedText);

        doReturn(Optional.of(article)).when(articleRepository).findById(id);
        doReturn(Optional.of(titleTranslatedText)).when(translatedTextRepository).findById(titleTranslatedTextId);
        doReturn(Optional.of(bodyTranslatedText)).when(translatedTextRepository).findById(bodyTranslatedTextId);
        doReturn(Optional.empty()).when(locateRepository).findByLanguageCode(langCode);

        // Verify

        articleService.getByIdAndLanguageCode(id, langCode);
    }

    @Test
    public void testDeleteById_performsDelete() {

        // Input data

        final Long id = 2L;

        doReturn(true).when(articleRepository).existsById(id);

        // Verify

        articleService.deleteById(id);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testDeleteByIncorrectId_throwsTheException() {

        // Input data

        final Long id = 2L;

        doReturn(false).when(articleRepository).existsById(id);

        // Verify

        articleService.deleteById(id);
    }

}
