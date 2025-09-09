package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @JsonProperty("id_producto")
    private Integer idProduct;

    @JsonProperty("titulo")
    private String title;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("precio")
    private double price;

    @JsonProperty("categoria")
    private CategoryDTO category;

    @JsonProperty("metadata")
    private String metadata;

    @JsonProperty("stock")
    private int stock;
}
