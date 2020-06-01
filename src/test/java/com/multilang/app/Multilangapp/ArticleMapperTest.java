package com.multilang.app.Multilangapp;

import com.multilang.app.Multilangapp.dto.ArticleDTO;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Article;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.mapper.ArticleMapper;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class ArticleMapperTest {

    @Mock
    private TranslatedTextRepository translatedTextRepository;

    private Mapper<Article, ArticleDTO> articleDTOMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        articleDTOMapper = new ArticleMapper(translatedTextRepository);
    }

    @Test
    public void testArticleMapperFrom() {

        // Input data

        final Long id = 2L;
        final Long titleTranslatedTextId = 3L;
        final Long bodyTranslatedTextId = 5L;
        final Date createdAt = new Date();

        // Construct Article object with aforementioned input data

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(id);
        articleDTO.setCreatedAt(createdAt);
        articleDTO.setBody(new TranslationDTO(2L, bodyTranslatedTextId,"some body text", "ua"));
        articleDTO.setTitle(new TranslationDTO(9L, titleTranslatedTextId,"some title text", "ua"));

        doReturn(new TranslatedText(titleTranslatedTextId)).when(translatedTextRepository).getOne(titleTranslatedTextId);
        doReturn(new TranslatedText(bodyTranslatedTextId)).when(translatedTextRepository).getOne(bodyTranslatedTextId);

        // Verify

        Article article = articleDTOMapper.from(articleDTO);

        assertEquals(article.getId(), id);
        assertEquals(article.getTranslatedTitle().getId(), titleTranslatedTextId);
        assertEquals(article.getTranslatedBody().getId(), bodyTranslatedTextId);
        assertEquals(article.getCreatedAt(), createdAt);
    }

    @Test
    public void testArticleMapperTo() {

        // Input data

        final Long id = 2L;
        final Date createdAt = new Date();

        Article article = new Article();
        article.setId(id);
        article.setCreatedAt(createdAt);

        // Verify

        ArticleDTO articleDTO = articleDTOMapper.to(article);

        assertEquals(articleDTO.getId(), article.getId());
        assertEquals(articleDTO.getCreatedAt(), article.getCreatedAt());
    }

}
