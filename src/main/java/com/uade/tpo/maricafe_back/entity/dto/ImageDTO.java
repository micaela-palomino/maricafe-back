package com.uade.tpo.maricafe_back.entity.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImageDTO {
    Long id;
    String file; // base64
}
