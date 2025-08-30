package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.cofigs.ModelMapperConfig;
import com.uade.tpo.maricafe_back.repository.OrdenRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {
    private final OrdenRepository ordenRepository;
    private final ModelMapperConfig modelMapper;

    public OrdenServiceImpl(OrdenRepository ordenRepository, ModelMapperConfig modelMapper) {
        this.ordenRepository = ordenRepository;
        this.modelMapper = modelMapper;
    }
}
