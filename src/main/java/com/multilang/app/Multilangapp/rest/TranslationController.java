package com.multilang.app.Multilangapp.rest;

import com.multilang.app.Multilangapp.dto.TranslationDTO;
import com.multilang.app.Multilangapp.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/translations")
public class TranslationController {

    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/{id}")
    public TranslationDTO getById(@PathVariable("id") Long id) {
        return translationService.getById(id);
    }

    @GetMapping("/{translatedTextId}/{languageCode}")
    public TranslationDTO getByTranslatedTextIdAndLanguageCode(@PathVariable("translatedTextId") Long id,
                                                               @PathVariable("languageCode") String languageCode) {
        return translationService.getByTranslatedTextAndLocate(id, languageCode);
    }

    @PostMapping
    public TranslationDTO createTranslation(@Valid TranslationDTO translationDTO) {
        return translationService.add(translationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        translationService.deleteById(id);
    }

}
