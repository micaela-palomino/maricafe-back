package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import com.uade.tpo.maricafe_back.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        // Por ahora, guarda lo que venga. Luego calculamos total y usuario.
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findMine() {
        // TEMP: devuelve todas. Después filtramos por usuario autenticado.
        return orderRepository.findAll();
    }

    @Override
    public Order findMyOrderById(Integer id) {
        // TEMP: busca por id sin validar dueño. Luego agregamos seguridad.
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }
}
