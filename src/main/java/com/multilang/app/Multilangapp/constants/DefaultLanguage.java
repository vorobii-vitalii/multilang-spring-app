package com.multilang.app.Multilangapp.constants;

import org.springframework.beans.factory.annotation.Value;

public interface DefaultLanguage {
    @Value("${multilang.default-lang}")
    String VALUE = "";
}
