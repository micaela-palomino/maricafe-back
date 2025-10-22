package com.uade.tpo.maricafe_back.controllers.config;

import com.uade.tpo.maricafe_back.entity.User;
import com.uade.tpo.maricafe_back.entity.dto.UserResponseDTO;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.dto.ProductDTO;
import com.uade.tpo.maricafe_back.entity.Discount;
import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
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

        // Configurar mapping de Product a ProductDTO (solo id, el resto por convención)
        mapper.createTypeMap(Product.class, ProductDTO.class)
                .addMapping(Product::getProductId, ProductDTO::setIdProduct);

        // Configurar mapping de Discount a DiscountDTO
        mapper.createTypeMap(Discount.class, DiscountDTO.class)
                .addMapping(src -> src.getProduct().getProductId(), DiscountDTO::setProductId);

        return mapper;
    }

}
