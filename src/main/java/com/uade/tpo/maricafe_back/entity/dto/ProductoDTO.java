package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    @JsonProperty("id_producto")
    private Integer idProducto;

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("precio")
    private double precio;

    @JsonProperty("categoria")
    private CategoriaDTO categoria;

    @JsonProperty("metadata")
    private String metadata;

    @JsonProperty("stock")
    private int stock;
}
