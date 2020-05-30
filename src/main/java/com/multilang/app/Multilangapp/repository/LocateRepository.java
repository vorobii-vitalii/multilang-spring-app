package com.multilang.app.Multilangapp.repository;

import com.multilang.app.Multilangapp.entity.Locate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocateRepository extends JpaRepository<Locate, Long> {
    Optional<Locate> findByLanguageCode(String languageCode);
    boolean existsByLanguageCode(String languageCode);
}
