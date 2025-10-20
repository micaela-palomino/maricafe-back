package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_attribute_values")
public class ProductAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer valueId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "attributeId", nullable = false)
    private ProductAttribute attribute;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String value;

    // Unique constraint to ensure one value per product-attribute combination
    @Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productId", "attributeId"})
    })
    public static class ProductAttributeValueTable {
    }
}
