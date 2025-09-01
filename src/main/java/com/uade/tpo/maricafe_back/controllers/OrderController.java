package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.service.IOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final IOrderService ordenService;

    public OrderController(IOrderService ordenService) {
        this.ordenService = ordenService;
    }
}
