package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @JsonProperty("product_id")
    private Integer idProduct;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private double price;

    @JsonProperty("category")
    private CategoryDTO category;

    @JsonProperty("stock")
    private int stock;

    // Información de descuentos (solo cuando hay descuentos asociado)
    @JsonProperty("discount_percentage")
    private Double discountPercentage; // en %

    @JsonProperty("old_price")
    private Double oldPrice; // precio antes del descuento

    @JsonProperty("new_price")
    private Double newPrice; // precio con el descuento aplicado (generalmente igual a price actual)
}
