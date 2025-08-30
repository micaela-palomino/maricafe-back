package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.repository.OrdenRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {
    private final OrdenRepository ordenRepository;

    public OrdenServiceImpl(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }
}
