package com.multilang.app.Multilangapp.mapper;

import com.multilang.app.Multilangapp.dto.LocateDTO;
import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;
import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.entity.Locate;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import com.multilang.app.Multilangapp.entity.Translation;
import com.multilang.app.Multilangapp.service.LocateService;
import com.multilang.app.Multilangapp.service.TranslatedTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslationMapper implements Mapper<Translation, TranslationDTO> {

    private final TranslatedTextService translatedTextService;
    private final LocateService locateService;
    private final Mapper<Locate, LocateDTO> locateDTOMapper;
    private final Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper;

    @Autowired
    public TranslationMapper(TranslatedTextService translatedTextService,
                             LocateService locateService,
                             Mapper<Locate, LocateDTO> locateDTOMapper,
                             Mapper<TranslatedText, TranslatedTextDTO> translatedTextDTOMapper) {
        this.translatedTextService = translatedTextService;
        this.locateService = locateService;
        this.locateDTOMapper = locateDTOMapper;
        this.translatedTextDTOMapper = translatedTextDTOMapper;
    }

    @Override
    public Translation from(TranslationDTO translationDTO) {

        Translation translation = new Translation();

        translation.setId(translationDTO.getId());
        translation.setText(translationDTO.getText());

        LocateDTO locateDTO = locateService.getByLanguageCode(translationDTO.getLanguageCode());
        translation.setLocate(locateDTOMapper.from(locateDTO));

        TranslatedTextDTO translatedText = translatedTextService.getById(translationDTO.getTranslatedTextId());
        translation.setTranslatedText(translatedTextDTOMapper.from(translatedText));

        return translation;
    }

    @Override
    public TranslationDTO to(Translation translation) {
        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setId(translation.getId());
        translationDTO.setLanguageCode(translation.getLocate().getLanguageCode());
        translationDTO.setTranslatedTextId(translation.getTranslatedText().getId());
        translationDTO.setText(translation.getText());
        return translationDTO;
    }

}
