package com.multilang.app.Multilangapp.repository;

import com.multilang.app.Multilangapp.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
}
