package com.multilang.app.Multilangapp.rest;

import com.multilang.app.Multilangapp.dto.ArticleDTO;
import com.multilang.app.Multilangapp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ArticleDTO getById(@PathVariable("id") Long id) {
        return articleService.getById(id);
    }

    @GetMapping("/{id}/{languageCode}")
    public ArticleDTO getByIdAndLanguageCode(@PathVariable("id") Long id,
                                             @PathVariable("languageCode") String languageCode) {
        return articleService.getByIdAndLanguageCode(id, languageCode);
    }

    @PostMapping
    public ArticleDTO add() {
        return articleService.add();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        articleService.deleteById(id);
    }

}
