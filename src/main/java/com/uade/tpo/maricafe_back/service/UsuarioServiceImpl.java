package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }
}
