package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.repository.OrdenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {
    private final OrdenRepository ordenRepository;
    private final ModelMapper modelMapper;

    public OrdenServiceImpl(OrdenRepository ordenRepository, ModelMapper modelMapper) {
        this.ordenRepository = ordenRepository;
        this.modelMapper = modelMapper;
    }
}
