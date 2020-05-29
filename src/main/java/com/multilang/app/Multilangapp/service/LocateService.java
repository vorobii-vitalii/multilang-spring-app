package com.multilang.app.Multilangapp.service;

import com.multilang.app.Multilangapp.entity.Locate;

import java.util.List;

public interface LocateService {
    Locate getById(Long id);
    List<Locate> getAll();
    Locate getByLanguageCode(String languageCode);
}
