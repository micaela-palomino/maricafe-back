package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.cofigs.ModelMapperConfig;
import com.uade.tpo.maricafe_back.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements IProductoService {
    private final ProductoRepository productoRepository;
    private final ModelMapperConfig modelMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository, ModelMapperConfig modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }
}
