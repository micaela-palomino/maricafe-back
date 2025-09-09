package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String title;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private String metadata;
    private int stock;
}
