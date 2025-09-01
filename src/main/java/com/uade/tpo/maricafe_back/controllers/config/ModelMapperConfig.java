package com.uade.tpo.maricafe_back.controllers.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    // Convierte dto a entidad o al revés
    // Cuando alguien en el proyecto necesite un ModelMapper, yo sé cómo crearlo y registrarlo como Bean.
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

