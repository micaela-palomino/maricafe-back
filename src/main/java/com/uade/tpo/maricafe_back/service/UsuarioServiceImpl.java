package com.uade.tpo.maricafe_back.service;


import com.uade.tpo.maricafe_back.repository.UserRepository;
import org.modelmapper.ModelMapper;
import com.uade.tpo.maricafe_back.controllers.config.ModelMapperConfig;
import com.uade.tpo.maricafe_back.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UsuarioServiceImpl(UserRepository usuarioRepository, ModelMapper modelMapper) {
        this.userRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }
}
