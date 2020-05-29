package com.multilang.app.Multilangapp.repository;

import com.multilang.app.Multilangapp.entity.Locate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocateRepository extends JpaRepository<Locate, Long> {
    Optional<Locate> findByLanguageCode(String languageCode);
}
