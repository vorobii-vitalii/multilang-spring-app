package com.multilang.app.Multilangapp.service.impl;

import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.exceptions.LocateNotFoundException;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocateServiceImpl implements LocateService {

    private final LocateRepository locateRepository;

    @Autowired
    public LocateServiceImpl(LocateRepository locateRepository) {
        this.locateRepository = locateRepository;
    }

    @Override
    public Locate getById(Long id) {
        return locateRepository.findById(id)
                .orElseThrow(LocateNotFoundException::new);
    }

    @Override
    public List<Locate> getAll() {
        return locateRepository.findAll();
    }

    @Override
    public Locate getByLanguageCode(String languageCode) {
        return locateRepository
                .findByLanguageCode(languageCode)
                .orElseThrow(LocateNotFoundException::new);
    }

}
