package com.multilang.app.Multilangapp.service;

import com.multilang.app.Multilangapp.dto.ArticleDTO;

public interface ArticleService {
    ArticleDTO getById(Long id);
    ArticleDTO getByIdAndLanguageCode(Long id, String languageCode);
    ArticleDTO add();
    void deleteById(Long id);
}
