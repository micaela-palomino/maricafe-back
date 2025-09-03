package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Order;
import java.util.List;

public interface IOrderService {
    Order create(Order order);
    List<Order> findMine();
    Order findMyOrderById(Integer id);
}
