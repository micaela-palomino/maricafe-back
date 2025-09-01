package com.uade.tpo.maricafe_back.service;


import com.uade.tpo.maricafe_back.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import com.uade.tpo.maricafe_back.controllers.config.ModelMapperConfig;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements IProductoService {
    private final ProductRepository productoRepository;
    private final ModelMapper modelMapper;

    public ProductoServiceImpl(ProductRepository productoRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }
}
