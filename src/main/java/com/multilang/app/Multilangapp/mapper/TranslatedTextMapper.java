package com.multilang.app.Multilangapp.mapper;

import com.multilang.app.Multilangapp.dto.TranslatedTextDTO;
import com.multilang.app.Multilangapp.entity.TranslatedText;
import org.springframework.stereotype.Component;

@Component
public class TranslatedTextMapper implements Mapper<TranslatedText, TranslatedTextDTO> {

    @Override
    public TranslatedText from(TranslatedTextDTO translatedTextDTO) {
        TranslatedText translatedText = new TranslatedText();
        translatedText.setId(translatedTextDTO.getId());
        return translatedText;
    }

    @Override
    public TranslatedTextDTO to(TranslatedText translatedText) {
        TranslatedTextDTO translatedTextDTO = new TranslatedTextDTO();
        translatedTextDTO.setId(translatedText.getId());
        return translatedTextDTO;
    }

}
