package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {

    @JsonProperty("discountId")
    private Integer discountId;

    @JsonProperty("productId")
    private Integer productId;

    @JsonProperty("discountPercentage")
    private double discountPercentage;
}
