package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDTO {

    @JsonProperty("id_orden")
    private Integer idOrden;

    @JsonProperty("fecha_orden")
    private String fechaOrden;

    @JsonProperty("precio_total")
    private double precioTotal;

    @JsonProperty("productos")
    private List<ProductoDTO> productos;
}
