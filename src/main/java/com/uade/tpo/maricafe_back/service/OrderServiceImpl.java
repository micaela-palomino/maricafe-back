package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.entity.OrderItem;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.User;
import com.uade.tpo.maricafe_back.entity.dto.CreateOrderDTO;
import com.uade.tpo.maricafe_back.entity.dto.OrderDTO;
import com.uade.tpo.maricafe_back.entity.dto.ItemDTO;
import com.uade.tpo.maricafe_back.entity.dto.OrderItemDTO;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import com.uade.tpo.maricafe_back.repository.OrderItemRepository;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import com.uade.tpo.maricafe_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No hay usuario autenticado");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername(); // En nuestro caso, username es el email

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userEmail));
    }

    @Override
    public OrderDTO create(CreateOrderDTO orderDto) {
        // Validar que hay productos en la orden
        if (orderDto.getItems() == null || orderDto.getItems().isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un producto");
        }

        // Obtener el usuario autenticado
        User authenticatedUser = getAuthenticatedUser();

        // Crear la orden primero
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .totalPrice(0.0) // Se calculara después
                .user(authenticatedUser)
                .build();

        Order created = orderRepository.save(order);

        // Procesar cada item de la orden
        double totalPrice = 0.0;
        List<ItemDTO> itemDTOs = new ArrayList<>();
        
        for (OrderItemDTO orderItemDto : orderDto.getItems()) {
            Product product = productRepository.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + orderItemDto.getProductId()));
            
            // Validar stock disponible
            if (product.getStock() < orderItemDto.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getTitle());
            }
            
            // Actualizar stock del producto
            product.setStock(product.getStock() - orderItemDto.getQuantity());
            productRepository.save(product);

            // Crear OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .order(created)
                    .product(product)
                    .quantity(orderItemDto.getQuantity())
                    .build();
            orderItemRepository.save(orderItem);

            // Sumar al precio total
            totalPrice += product.getPrice() * orderItemDto.getQuantity();
            
            // Crear ItemDTO para la respuesta
            ItemDTO itemDTO = ItemDTO.builder()
                    .name(product.getTitle())
                    .quantity(orderItemDto.getQuantity())
                    .build();
            itemDTOs.add(itemDTO);
        }

        // Actualizar el precio total de la orden
        created.setTotalPrice(totalPrice);
        orderRepository.save(created);
        
        // Crear OrderDTO manualmente con los ItemDTO construidos
        return OrderDTO.builder()
                .orderId(created.getOrderId())
                .orderDate(created.getOrderDate())
                .totalPrice(created.getTotalPrice())
                .items(itemDTOs)
                .build();
    }

    @Override
    public List<OrderDTO> findMine() {
        // Obtener el usuario autenticado
        User authenticatedUser = getAuthenticatedUser();

        // Buscar solo las ordenes del usuario autenticado
        return orderRepository.findByUser(authenticatedUser)
                .stream()
                .map(order -> {
                    // Obtener los OrderItems de esta orden
                    List<OrderItem> orderItems = orderItemRepository.findByOrderOrderId(order.getOrderId());
                    
                    // Crear ItemDTO para cada OrderItem
                    List<ItemDTO> itemDTOs = orderItems.stream()
                            .map(orderItem -> ItemDTO.builder()
                                    .name(orderItem.getProduct().getTitle())
                                    .quantity(orderItem.getQuantity())
                                    .build())
                            .toList();
                    
                    return OrderDTO.builder()
                            .orderId(order.getOrderId())
                            .orderDate(order.getOrderDate())
                            .totalPrice(order.getTotalPrice())
                            .items(itemDTOs)
                            .build();
                })
                .toList();
    }

    @Override
    public OrderDTO findById(Integer id) {
        // Solo admins pueden acceder a cualquier orden por ID
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la orden con id: " + id));
        
        // Obtener los OrderItems de esta orden
        List<OrderItem> orderItems = orderItemRepository.findByOrderOrderId(order.getOrderId());
        
        // Crear ItemDTO para cada OrderItem
        List<ItemDTO> itemDTOs = orderItems.stream()
                .map(orderItem -> ItemDTO.builder()
                        .name(orderItem.getProduct().getTitle())
                        .quantity(orderItem.getQuantity())
                        .build())
                .toList();
        
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .items(itemDTOs)
                .build();
    }

    @Override
    public void deleteOrderById(Integer id) {
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("La orden con id: " + id + " no existe");
        }
        orderRepository.deleteById(id);
    }
}
