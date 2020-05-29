package com.multilang.app.Multilangapp.repository;

import com.multilang.app.Multilangapp.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
