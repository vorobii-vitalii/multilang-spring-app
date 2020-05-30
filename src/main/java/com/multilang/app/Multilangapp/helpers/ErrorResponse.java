package com.multilang.app.Multilangapp.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> {
    private T cause;
    private Long timestamp;
}
