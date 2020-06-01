package com.multilang.app.Multilangapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private Long id;

    private TranslationDTO title;

    private TranslationDTO body;

    private Date createdAt;

    private Long titleTranslatedTextId;

    private Long bodyTranslatedTextId;

}
