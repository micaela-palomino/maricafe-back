package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

    @JsonProperty("titulo")
    private String title;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("precio")
    private double price;

    @JsonProperty("categoria_id")
    private Integer categoryId;

    @JsonProperty("metadata")
    private String metadata;

    @JsonProperty("stock")
    private int stock;
}
