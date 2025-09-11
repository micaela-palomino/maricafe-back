package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @JsonProperty("orderId")
    private Integer orderId;

    @JsonProperty("orderDate")
    private LocalDate orderDate;

    @JsonProperty("totalPrice")
    private double totalPrice;

    @JsonProperty("items")
    private List<ItemDTO> items;
}
