package com.multilang.app.Multilangapp.service.impl;

import com.multilang.app.Multilangapp.constants.DefaultLanguage;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.entity.Translation;
import com.multilang.app.Multilangapp.exceptions.LocateNotFoundException;
import com.multilang.app.Multilangapp.exceptions.TranslatedTextNotFoundException;
import com.multilang.app.Multilangapp.exceptions.TranslationNotFoundException;
import com.multilang.app.Multilangapp.mapper.Mapper;
import com.multilang.app.Multilangapp.repository.LocateRepository;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.repository.TranslationRepository;
import com.multilang.app.Multilangapp.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;
    private final Mapper<Translation, TranslationDTO> translationDTOMapper;
    private final TranslatedTextRepository translatedTextRepository;
    private final LocateRepository locateRepository;

    @Autowired
    public TranslationServiceImpl(TranslationRepository translationRepository,
                                  Mapper<Translation, TranslationDTO> translationDTOMapper,
                                  TranslatedTextRepository translatedTextRepository,
                                  LocateRepository locateRepository) {
        this.translationRepository = translationRepository;
        this.translationDTOMapper = translationDTOMapper;
        this.translatedTextRepository = translatedTextRepository;
        this.locateRepository = locateRepository;
    }

    @Override
    public TranslationDTO getById(Long id) {
        Translation translation = translationRepository
                                            .findById(id)
                                            .orElseThrow(TranslationNotFoundException::new);
        return translationDTOMapper.to(translation);
    }

    @Override
    public List<TranslationDTO> getAllByParentTranslatedText(Long translatedTextId) {
        TranslatedText translatedText = translatedTextRepository
                                                .findById(translatedTextId)
                                                .orElseThrow(TranslatedTextNotFoundException::new);
        Set<Translation> translationSet = translatedText.getTranslations();
        return translationSet.stream().map(translationDTOMapper::to).collect(Collectors.toList());
    }

    @Override
    public TranslationDTO getByTranslatedTextAndLocate(Long translatedTextId, String langCode) {
        TranslatedText translatedText = translatedTextRepository
                .findById(translatedTextId)
                .orElseThrow(TranslatedTextNotFoundException::new);
        Locate locate = locateRepository
                        .findByLanguageCode( (langCode != null && !langCode.trim().equals("")) ? langCode : DefaultLanguage.VALUE)
                        .orElseThrow(LocateNotFoundException::new);
        Translation translation = translationRepository
                                        .findByTranslatedTextAndLocate(translatedText, locate)
                                        .orElseThrow(TranslationNotFoundException::new);
        return translationDTOMapper.to(translation);
    }

    @Override
    public TranslationDTO add(TranslationDTO translationDTO) {
        Translation translation = translationRepository.save(translationDTOMapper.from(translationDTO));
        return translationDTOMapper.to(translation);
    }

    @Override
    public void deleteById(Long id) {
        if (!translationRepository.existsById(id))
            throw new TranslationNotFoundException();
        translationRepository.deleteById(id);
    }

}
