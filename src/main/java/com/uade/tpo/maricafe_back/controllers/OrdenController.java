package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.service.IOrdenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrdenController {
    private final IOrdenService ordenService;

    public OrdenController(IOrdenService ordenService) {
        this.ordenService = ordenService;
    }
}
