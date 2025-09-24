package com.uade.tpo.maricafe_back.controllers.config;

import com.uade.tpo.maricafe_back.entity.User;
import com.uade.tpo.maricafe_back.entity.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    // Convierte dto a entidad o al revés
    // Cuando alguien en el proyecto necesite un ModelMapper, yo sé cómo crearlo y registrarlo como Bean.
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        
        // Configurar el mapping de User a UserResponseDTO (sin password)
        mapper.createTypeMap(User.class, UserResponseDTO.class)
                .addMapping(User::getUserId, UserResponseDTO::setUserId)
                .addMapping(User::getFirstName, UserResponseDTO::setFirstName)
                .addMapping(User::getLastName, UserResponseDTO::setLastName)
                .addMapping(User::getEmail, UserResponseDTO::setEmail)
                .addMapping(User::getRole, UserResponseDTO::setRole);
        
        return mapper;
    }

}

