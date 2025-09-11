package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {

    @JsonProperty("discount_id")
    private Integer discountId;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("discount_percentage")
    private double discountPercentage;
}
