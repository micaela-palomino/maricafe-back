package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.User;
import com.uade.tpo.maricafe_back.entity.dto.CreateOrderDTO;
import com.uade.tpo.maricafe_back.entity.dto.OrderDTO;
import com.uade.tpo.maricafe_back.exceptions.ResourceNotFoundException;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import com.uade.tpo.maricafe_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper; // esto es para mapear de entidad a dto y viceversa

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

        List<Product> products = orderDto.getItems().stream()
            .map(item -> productRepository.findById(item.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + item.getProductId())))
            .toList();

        // actualizar stock de los productos
        double totalPrice = 0.0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            var item = orderDto.getItems().get(i);
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            // sumar el precio del producto al total
            totalPrice += product.getPrice() * item.getQuantity();
        }

        // Crear la orden con el usuario autenticado
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .totalPrice(totalPrice)
                .products(products)
                .user(authenticatedUser)
                .build();

        Order created = orderRepository.save(order);
        return modelMapper.map(created, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findMine() {
        // Obtener el usuario autenticado
        User authenticatedUser = getAuthenticatedUser();

        // Buscar solo las órdenes del usuario autenticado
        return orderRepository.findByUser(authenticatedUser)
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
    }

    @Override
    public OrderDTO findById(Integer id) {
        // Solo admins pueden acceder a cualquier orden por ID
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No existe la orden con id: " + id));
    }

    @Override
    public void deleteOrderById(Integer id) {
        if(!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("La orden con id: " + id + " no existe");
        }
        orderRepository.deleteById(id);
    }
}
