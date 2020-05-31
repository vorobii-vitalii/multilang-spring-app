package com.multilang.app.Multilangapp.service.impl;

import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.exceptions.TranslatedTextNotFoundException;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.service.TranslatedTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslatedTextServiceImpl implements TranslatedTextService {

    private final TranslatedTextRepository translatedTextRepository;
    private final Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper;

    @Autowired
    public TranslatedTextServiceImpl( TranslatedTextRepository translatedTextRepository,
                                      Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper) {
        this.translatedTextRepository = translatedTextRepository;
        this.translatedTextDTOMapper = translatedTextDTOMapper;
    }

    @Override
    public TranslatedTextDTO getById(Long id) {
        TranslatedText translatedText = translatedTextRepository
                                            .findById(id)
                                            .orElseThrow(TranslatedTextNotFoundException::new);
        return translatedTextDTOMapper.to(translatedText);
    }

    @Override
    public boolean existsById(Long id) {
        return translatedTextRepository.existsById(id);
    }

}
