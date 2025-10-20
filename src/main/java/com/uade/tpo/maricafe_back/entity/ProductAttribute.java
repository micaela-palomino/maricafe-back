package com.uade.tpo.maricafe_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_attributes")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attributeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dataType; // "text", "number", "boolean", "select"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean required;

    // JSON string for select options (e.g., ["Small", "Medium", "Large"])
    @Column(columnDefinition = "TEXT")
    private String selectOptions;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductAttributeValue> attributeValues = new ArrayList<>();
}
