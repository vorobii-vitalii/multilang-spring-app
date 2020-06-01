package com.multilang.app.Multilangapp.service.impl;

import com.multilang.app.Multilangapp.dto.ArticleDTO;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Article;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.exceptions.ArticleNotFoundException;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.repository.ArticleRepository;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.service.ArticleService;
import com.multilang.app.Multilangapp.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TranslationService translationService;
    private final TranslatedTextRepository translatedTextRepository;
    private final Mapper<Article, ArticleDTO> articleDTOMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              TranslationService translationService,
                              TranslatedTextRepository translatedTextRepository,
                              Mapper<Article, ArticleDTO> articleDTOMapper) {
        this.articleRepository = articleRepository;
        this.translationService = translationService;
        this.translatedTextRepository = translatedTextRepository;
        this.articleDTOMapper = articleDTOMapper;
    }

    @Override
    public ArticleDTO getByIdAndLanguageCode(Long id, String languageCode) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        Long titleId = article.getTranslatedTitle().getId();
        Long bodyId = article.getTranslatedBody().getId();
        ArticleDTO articleDTO = articleDTOMapper.to(article);
        TranslationDTO titleTranslation = translationService.getByTranslatedTextAndLocate(titleId, languageCode);
        TranslationDTO bodyTranslation = translationService.getByTranslatedTextAndLocate(bodyId, languageCode);
        articleDTO.setTitle(titleTranslation);
        articleDTO.setBody(bodyTranslation);
        return articleDTO;
    }

    @Override
    public ArticleDTO add() {
        Article article = new Article();
        TranslatedText textTitle = translatedTextRepository.save(new TranslatedText());
        TranslatedText textBody = translatedTextRepository.save(new TranslatedText());
        article.setTranslatedTitle(textTitle);
        article.setTranslatedBody(textBody);
        return articleDTOMapper.to(articleRepository.save(article));
    }

    @Override
    public void deleteById(Long id) {
        if (!articleRepository.existsById(id))
            throw new ArticleNotFoundException();
        articleRepository.deleteById(id);
    }

}
