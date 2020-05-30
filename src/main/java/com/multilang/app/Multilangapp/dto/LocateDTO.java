package com.multilang.app.Multilangapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocateDTO {

    private Long id;

    @NotBlank
    private String languageCode;

}
