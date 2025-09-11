package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @JsonProperty("productId")
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
}
