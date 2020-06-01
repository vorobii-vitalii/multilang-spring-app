package com.multilang.app.Multilangapp.service;

import com.multilang.app.Multilangapp.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    ArticleDTO getByIdAndLanguageCode(Long id, String languageCode);
    ArticleDTO add();
    void deleteById(Long id);
}
