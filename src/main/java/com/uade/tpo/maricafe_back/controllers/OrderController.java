package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.ApiResponseDTO;
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
    public ApiResponseDTO<OrderDTO> create(@RequestBody CreateOrderDTO order) {
        OrderDTO created = ordenService.create(order);
        return ApiResponseDTO.success("Orden creada con éxito", created);
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

    // 4.4 [GET] "/orders/user/{id}" -> Obtener una orden del usuario autenticado
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getMyOrder(@PathVariable Integer id) {
        return ordenService.findMyOrderById(id);
    }

    @DeleteMapping("/{id}")
    public ApiResponseDTO<Void> delete(@PathVariable Integer id) {
        ordenService.deactivateOrder(id);
        return ApiResponseDTO.success("Orden desactivada con éxito");
    }

    @PutMapping("/{id}/finalize")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDTO<OrderDTO> finalizeOrder(@PathVariable Integer id) {
        OrderDTO updated = ordenService.finalizeOrder(id);
        return ApiResponseDTO.success("Orden finalizada con éxito", updated);
    }

    // Endpoints para administradores
    @GetMapping("/admin/active")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllActiveOrders() {
        return ordenService.findAllActiveOrders();
    }

    @GetMapping("/admin/inactive")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllInactiveOrders() {
        return ordenService.findAllInactiveOrders();
    }

}
