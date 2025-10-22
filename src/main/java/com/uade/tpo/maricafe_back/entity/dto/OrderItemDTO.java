package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("unit_price")
    private Double unitPrice;
}
