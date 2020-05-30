package com.multilang.app.Multilangapp.service;

import com.multilang.app.Multilangapp.dto.LocateDTO;

import java.util.List;

public interface LocateService {
    LocateDTO add(LocateDTO locateDTO);
    LocateDTO getById(Long id);
    List<LocateDTO> getAll();
    LocateDTO getByLanguageCode(String languageCode);
}
