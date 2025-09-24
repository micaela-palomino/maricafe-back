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

    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("order_date")
    private LocalDate orderDate;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("items")
    private List<ItemDTO> items;
}
