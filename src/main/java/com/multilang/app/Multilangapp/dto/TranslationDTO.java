package com.multilang.app.Multilangapp.dto;

import com.multilang.app.Multilangapp.validation.LanguageCode;
import com.multilang.app.Multilangapp.validation.TranslatedText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslationDTO {

    private Long id;

    @TranslatedText
    private Long translatedTextId;

    @NotBlank
    private String text;

    @LanguageCode
    private String languageCode;

}
