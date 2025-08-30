package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
