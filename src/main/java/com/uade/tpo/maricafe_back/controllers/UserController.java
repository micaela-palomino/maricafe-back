package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    private final IUserService usuarioService;

    public UserController(IUserService usuarioService) {
        this.usuarioService = usuarioService;
    }
}
