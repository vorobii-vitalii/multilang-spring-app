package com.multilang.app.Multilangapp.mapper;

public interface Mapper<U, J> {
    U from(J j);
    J to(U u);
}
