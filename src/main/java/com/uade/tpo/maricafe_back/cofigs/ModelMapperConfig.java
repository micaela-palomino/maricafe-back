package com.uade.tpo.maricafe_back.cofigs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    // Convierte dto a entidad o al revés
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /* EJEMPLO DE USO:
        @Service
        public class UsuarioService {

        private final ModelMapper modelMapper;
        private final UsuarioRepository usuarioRepository;

        public UsuarioService(ModelMapper modelMapper, UsuarioRepository usuarioRepository) {
            this.modelMapper = modelMapper;
            this.usuarioRepository = usuarioRepository;
        }

        Usuario usuario = usuarioRepository.findById(id);
        modelMapper.map(usuario, UsuarioDTO.class);
     */
}

