package com.multilang.app.Multilangapp.mapper;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.entity.Translation;
import com.multilang.app.Multilangapp.repository.TranslatedTextRepository;
import com.multilang.app.Multilangapp.service.LocateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslationMapper implements Mapper<Translation, TranslationDTO> {

    private final TranslatedTextRepository translatedTextRepository;
    private final LocateService locateService;
    private final Mapper<Locate, LocateDTO> locateDTOMapper;

    @Autowired
    public TranslationMapper(TranslatedTextRepository translatedTextRepository, LocateService locateService, Mapper<Locate, LocateDTO> locateDTOMapper) {
        this.translatedTextRepository = translatedTextRepository;
        this.locateService = locateService;
        this.locateDTOMapper = locateDTOMapper;
    }

    @Override
    public Translation from(TranslationDTO translationDTO) {
        Translation translation = new Translation();
        translation.setId(translationDTO.getId());
        translation.setText(translationDTO.getText());
        LocateDTO locateDTO = locateService.getByLanguageCode(translationDTO.getLanguageCode());
        translation.setLocate(locateDTOMapper.from(locateDTO));
        TranslatedText translatedText = translatedTextRepository.getOne(translationDTO.getTranslatedTextId());
        translation.setTranslatedText(translatedText);
        return translation;
    }

    @Override
    public TranslationDTO to(Translation translation) {
        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setId(translation.getId());
        translationDTO.setLanguageCode(translation.getLocate().getLanguageCode());
        translationDTO.setText(translation.getText());
        return translationDTO;
    }

}
