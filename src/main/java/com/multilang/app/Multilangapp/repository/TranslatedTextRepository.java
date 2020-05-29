package com.multilang.app.Multilangapp.repository;

import com.multilang.app.Multilangapp.entity.TranslatedText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslatedTextRepository extends JpaRepository <TranslatedText, Long>{
}
