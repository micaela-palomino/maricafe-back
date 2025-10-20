package com.uade.tpo.maricafe_back.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeDTO {

    @JsonProperty("attribute_id")
    private Integer attributeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("data_type")
    private String dataType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("required")
    private boolean required;

    @JsonProperty("select_options")
    private List<String> selectOptions;

    @JsonProperty("category_id")
    private Integer categoryId;
}
