package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.CreateOrderDTO;
import com.uade.tpo.maricafe_back.entity.dto.OrderDTO;
import com.uade.tpo.maricafe_back.service.IOrderService; // el nombre que vos usaste
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor // si no usás Lombok, borralo y dejá tu constructor manual
public class OrderController {

    private final IOrderService ordenService;

    // 4.1 [POST] "/orders" -> Crear orden del usuario autenticado
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@RequestBody CreateOrderDTO order) {
        return ordenService.create(order);
    }

    // 4.2 [GET] "/user/orders" -> Listar órdenes del usuario autenticado
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> myOrders() {
        return ordenService.findMine();
    }

    // 4.3 [GET] "/orders/{id}" -> Obtener una orden por id (solo ADMIN)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOne(@PathVariable Integer id) {

        return ordenService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        ordenService.deleteOrderById(id);
    }

}
