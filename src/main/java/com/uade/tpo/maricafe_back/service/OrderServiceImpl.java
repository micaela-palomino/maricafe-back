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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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

    private OrderDTO convertOrderToDTO(Order order) {
        // Obtener los OrderItems de esta orden
        List<OrderItem> orderItems = orderItemRepository.findByOrderOrderId(order.getOrderId());
        
        // Crear ItemDTO para cada OrderItem
        List<ItemDTO> itemDTOs = orderItems.stream()
                .map(orderItem -> ItemDTO.builder()
                        .name(orderItem.getProduct().getTitle())
                        .quantity(orderItem.getQuantity())
                        .unitPrice(orderItem.getUnitPrice())
                        .build())
                .toList();
        
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .active(order.isActive())
                .items(itemDTOs)
                .userId(order.getUser() != null ? order.getUser().getUserId() : null)
                .userName(order.getUser() != null ? order.getUser().getFirstName() + " " + order.getUser().getLastName() : null)
                .userEmail(order.getUser() != null ? order.getUser().getEmail() : null)
                .build();
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
                .orderDate(LocalDateTime.now())
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

            // Usar el precio unitario proporcionado en la request, o el precio del producto si no se proporciona
            double unitPrice = orderItemDto.getUnitPrice() != null ? orderItemDto.getUnitPrice() : product.getPrice();

            // Crear OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .order(created)
                    .product(product)
                    .quantity(orderItemDto.getQuantity())
                    .unitPrice(unitPrice)
                    .build();
            orderItemRepository.save(orderItem);

            // Sumar al precio total usando el precio unitario
            totalPrice += unitPrice * orderItemDto.getQuantity();
            
            // Crear ItemDTO para la respuesta
            ItemDTO itemDTO = ItemDTO.builder()
                    .name(product.getTitle())
                    .quantity(orderItemDto.getQuantity())
                    .unitPrice(unitPrice)
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
                .active(created.isActive())
                .items(itemDTOs)
                .build();
    }

    @Override
    public List<OrderDTO> findMine() {
        // Obtener el usuario autenticado
        User authenticatedUser = getAuthenticatedUser();

        // Buscar solo las ordenes activas del usuario autenticado
        return orderRepository.findByUserAndActiveTrue(authenticatedUser)
                .stream()
                .map(this::convertOrderToDTO)
                .toList();
    }

    @Override
    public OrderDTO findById(Integer id) {
        // Solo admins pueden acceder a cualquier orden activa por ID
        Order order = orderRepository.findByOrderIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la orden activa con id: " + id));
        
        return convertOrderToDTO(order);
    }

    @Override
    public OrderDTO findMyOrderById(Integer id) {
        // Obtener el usuario autenticado
        User authenticatedUser = getAuthenticatedUser();
        
        // Buscar la orden del usuario autenticado
        Order order = orderRepository.findByOrderIdAndUserAndActiveTrue(id, authenticatedUser)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la orden activa con id: " + id + " para el usuario actual"));
        
        return convertOrderToDTO(order);
    }

    @Override
    public void deactivateOrder(Integer id) {
        Order order = orderRepository.findByOrderIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("La orden con id: " + id + " no existe o ya está inactiva"));
        
        // Marcar como inactiva (soft delete)
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> findAllActiveOrders() {
        // Solo para administradores - obtener todas las órdenes activas
        return orderRepository.findByActiveTrue()
                .stream()
                .map(this::convertOrderToDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> findAllInactiveOrders() {
        // Solo para administradores - obtener todas las órdenes inactivas
        return orderRepository.findByActiveFalse()
                .stream()
                .map(this::convertOrderToDTO)
                .toList();
    }
}
