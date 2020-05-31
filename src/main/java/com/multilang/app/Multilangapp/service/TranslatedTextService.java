package com.multilang.app.Multilangapp.service;

import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;

public interface TranslatedTextService {
    TranslatedTextDTO getById(Long id);
    boolean existsById(Long id);
}
