package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.service.IProductoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductoController {
    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }
}
