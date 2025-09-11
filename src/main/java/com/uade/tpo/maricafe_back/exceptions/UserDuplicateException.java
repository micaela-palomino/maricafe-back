package com.uade.tpo.maricafe_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserDuplicateException extends RuntimeException {
    public UserDuplicateException(String email) {
        super("Ya existe un usuario registrado con este email: " + email);
    }
}
