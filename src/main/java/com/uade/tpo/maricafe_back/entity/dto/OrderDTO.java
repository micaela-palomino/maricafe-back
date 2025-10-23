package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("items")
    private List<ItemDTO> items;

    // User information
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_email")
    private String userEmail;
}
