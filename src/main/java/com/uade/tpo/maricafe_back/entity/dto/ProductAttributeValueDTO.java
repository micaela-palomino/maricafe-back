package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeValueDTO {

    @JsonProperty("value_id")
    private Integer valueId;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("attribute")
    private ProductAttributeDTO attribute;

    @JsonProperty("value")
    private String value;
}
