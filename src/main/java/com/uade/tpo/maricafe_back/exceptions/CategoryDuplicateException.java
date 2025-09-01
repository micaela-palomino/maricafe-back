package com.uade.tpo.maricafe_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryDuplicateException extends RuntimeException {
    public CategoryDuplicateException(String name) {
        super("Ya existe una categoría con nombre: " + name);
    }
}
