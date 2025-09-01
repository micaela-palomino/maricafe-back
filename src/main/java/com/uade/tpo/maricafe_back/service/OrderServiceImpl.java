package com.uade.tpo.maricafe_back.service;
import org.modelmapper.ModelMapper;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository ordenRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository ordenRepository, ModelMapper modelMapper) {
        this.ordenRepository = ordenRepository;

        this.modelMapper = modelMapper;
    }
}
