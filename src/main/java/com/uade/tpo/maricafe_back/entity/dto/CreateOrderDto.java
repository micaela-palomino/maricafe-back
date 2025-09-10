package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    @JsonProperty("productos")
    private List<ProductDTO> productos;
}
