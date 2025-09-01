package com.uade.tpo.maricafe_back.service;
import org.modelmapper.ModelMapper;
import com.uade.tpo.maricafe_back.controllers.config.ModelMapperConfig;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {
    private final OrderRepository ordenRepository;
    private final ModelMapper modelMapper;

    public OrdenServiceImpl(OrderRepository ordenRepository, ModelMapper modelMapper) {
        this.ordenRepository = ordenRepository;

        this.modelMapper = modelMapper;
    }
}
