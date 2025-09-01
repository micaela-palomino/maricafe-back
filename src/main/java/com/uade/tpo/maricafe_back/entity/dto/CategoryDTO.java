package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @JsonProperty("id_categoria")
    private Integer categoryId;

    @JsonProperty("name")
    private String name;
}
