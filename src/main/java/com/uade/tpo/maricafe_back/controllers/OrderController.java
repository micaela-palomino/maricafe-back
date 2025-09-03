package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.Order; // usamos la entidad por ahora
import com.uade.tpo.maricafe_back.service.IOrderService; // el nombre que vos usaste
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // si no usás Lombok, borralo y dejá tu constructor manual
public class OrderController {

    private final IOrderService ordenService;

    // 4.1 [POST] "/orders" -> Crear orden del usuario autenticado
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody Order order) {
        // En el próximo paso vamos a cambiar Order por un DTO si querés.
        // Por ahora delegamos y listo.
        return ordenService.create(order);
    }

    // 4.2 [GET] "/user/orders" -> Listar órdenes del usuario autenticado
    @GetMapping("/user/orders")
    public List<Order> myOrders() {
        return ordenService.findMine();
    }

    // 4.3 [GET] "/orders/{id}" -> Obtener una orden propia por id
    @GetMapping("/orders/{id}")
    public Order getOne(@PathVariable Integer id) {
        return ordenService.findMyOrderById(id);
    }
}
