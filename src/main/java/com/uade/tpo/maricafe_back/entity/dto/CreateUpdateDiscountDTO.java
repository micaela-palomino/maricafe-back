package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateDiscountDTO {

    @JsonProperty("discountPercentage")
    private double discountPercentage;
}
