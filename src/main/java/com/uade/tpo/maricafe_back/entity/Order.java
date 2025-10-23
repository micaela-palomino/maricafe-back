package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private LocalDateTime orderDate;
    private double totalPrice;
    
    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true; // Por defecto las órdenes están activas

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
