package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.service.IProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {
    private final IProductService productoService;

    public ProductController(IProductService productoService) {
        this.productoService = productoService;
    }
}
