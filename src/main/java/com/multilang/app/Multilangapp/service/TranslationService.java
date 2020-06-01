package com.multilang.app.Multilangapp.service;

import com.multilang.app.Multilangapp.dto.TranslationDTO;

import java.util.List;

public interface TranslationService {

    TranslationDTO getById(Long id);

    List<TranslationDTO> getAllByParentTranslatedText(Long translatedTextId);

    TranslationDTO getByTranslatedTextAndLocate(Long translatedTextId, String langCode);

    TranslationDTO add(TranslationDTO translationDTO);

    void deleteById(Long id);

}
