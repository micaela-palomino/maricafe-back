package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.CreateOrderDTO;
import com.uade.tpo.maricafe_back.entity.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    OrderDTO create(CreateOrderDTO order);
    List<OrderDTO> findMine();
    OrderDTO findById(Integer id);
    OrderDTO findMyOrderById(Integer id);
    void deactivateOrder(Integer id);
    OrderDTO finalizeOrder(Integer id);
    
    // Métodos para administradores
    List<OrderDTO> findAllActiveOrders();
    List<OrderDTO> findAllInactiveOrders();
}
