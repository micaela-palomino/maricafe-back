package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.cofigs.ModelMapperConfig;
import com.uade.tpo.maricafe_back.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapperConfig modelMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ModelMapperConfig modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }
}
