package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.dto.CreateOrderDto;
import com.uade.tpo.maricafe_back.entity.dto.OrderDTO;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper; // esto es para mapear de entidad a dto y viceversa

    @Override
    public OrderDTO create(CreateOrderDto orderDto) {
        // Validar que hay productos en la orden
        if (orderDto.getProductos() == null || orderDto.getProductos().isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un producto");
        }

        List<Product> products = orderDto.getProductos().stream()
            .map(productDTO -> modelMapper.map(productDTO, Product.class)).toList();

        // Calcular el total automáticamente usando streams
        double totalPrice = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        // Crear la orden
        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .totalPrice(totalPrice)
                .products(products)
                .user(null) // TODO: Obtener usuario autenticado
                .build();

        Order created = orderRepository.save(order);
        return modelMapper.map(created, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findMine() {
        // TEMP: devuelve todas. Después filtramos por usuario autenticado.

        return orderRepository.findAll()
                .stream()
                .map(order ->modelMapper.map(order, OrderDTO.class))
                .toList();
    }

    @Override
    public OrderDTO findMyOrderById(Integer id) {
        // TEMP: busca por id sin validar dueño. Luego agregamos seguridad.
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .orElseThrow(()-> new ResourceNotFoundException("Order not Found"));
    }

    @Override
    public void deleteOrderById(Integer id) {
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("Doesn't exist: " + id);
        }
        orderRepository.deleteById(id);
    }
}
