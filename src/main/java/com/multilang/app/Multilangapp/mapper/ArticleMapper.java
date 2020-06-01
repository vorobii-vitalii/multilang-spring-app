package com.multilang.app.Multilangapp.mapper;

import com.multilang.app.Multilangapp.dto.ArticleDTO;
import com.multilang.app.Multilangapp.entity.Article;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper implements Mapper<Article, ArticleDTO> {

    private final TranslatedTextRepository translatedTextRepository;

    @Autowired
    public ArticleMapper(TranslatedTextRepository translatedTextRepository) {
        this.translatedTextRepository = translatedTextRepository;
    }

    @Override
    public Article from(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setCreatedAt(articleDTO.getCreatedAt());

        TranslatedText translatedTitle = translatedTextRepository.getOne(articleDTO.getTitle().getTranslatedTextId());
        TranslatedText translatedBody = translatedTextRepository.getOne(articleDTO.getBody().getTranslatedTextId());

        article.setTranslatedTitle(translatedTitle);
        article.setTranslatedBody(translatedBody);

        return article;
    }

    @Override
    public ArticleDTO to(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setTitleTranslatedTextId(article.getTranslatedTitle().getId());
        articleDTO.setBodyTranslatedTextId(article.getTranslatedBody().getId());
        // Title and body are missed intentionally
        return articleDTO;
    }

}
