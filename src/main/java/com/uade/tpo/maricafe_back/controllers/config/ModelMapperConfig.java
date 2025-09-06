package com.uade.tpo.maricafe_back.controllers.config;

import com.uade.tpo.maricafe_back.entity.User;
import com.uade.tpo.maricafe_back.entity.dto.UserDTO;
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
        
        // Configure User to UserDTO mapping
        mapper.createTypeMap(User.class, UserDTO.class)
                .addMapping(User::getUserId, UserDTO::setUserId)
                .addMapping(User::getFirstName, UserDTO::setFirstName)
                .addMapping(User::getLastName, UserDTO::setLastName)
                .addMapping(User::getEmail, UserDTO::setEmail)
                .addMapping(User::getRole, UserDTO::setRole)
                .addMapping(src -> null, UserDTO::setPassword); // Don't expose password in responses
        
        return mapper;
    }

}

