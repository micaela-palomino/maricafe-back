package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.entity.dto.CreateOrderDto;
import com.uade.tpo.maricafe_back.entity.dto.OrderDTO;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper; // esto es para mapear de entidad a dto y viceversa

    @Override
    public OrderDTO create(CreateOrderDto orderDto) {
        // Por ahora, guarda lo que venga. Luego calculamos total y usuario.
        Order order = modelMapper.map(orderDto, Order.class); // Convertir dto (sin id) hacia entidad para la bd
        Order created = orderRepository.save(order); // Guardas la orden en la db y guardas la entidad creada en una variable
        return modelMapper.map(created, OrderDTO.class); // Retornas el que guardaste PERO lo mapeas a dto (con id)
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

    @Override
    public void deleteOrderById(Integer id) {
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("No existe: " + id);
        }
        orderRepository.deleteById(id);

    }
    }

