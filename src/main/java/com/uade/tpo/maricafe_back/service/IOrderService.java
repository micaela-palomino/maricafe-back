package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.CreateOrderDto;
import com.uade.tpo.maricafe_back.entity.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    OrderDTO create(CreateOrderDto order);
    List<OrderDTO> findMine();
    OrderDTO findById(Integer id);
    void deleteOrderById(Integer id);
}
