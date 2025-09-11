package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Discount;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import com.uade.tpo.maricafe_back.repository.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DiscountServiceImpl implements IDiscountService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    public DiscountServiceImpl(ProductRepository productRepository, DiscountRepository discountRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DiscountDTO createDiscount(Integer productId, double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("discountPercentage debe estar entre 0 y 100");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("El producto: " + productId + " no fue encontrado"));

        Discount discount = Discount.builder()
                .product(product)
                .discountPercentage(percentage)
                .build();

        discount = discountRepository.save(discount);

        double precioFinal = product.getPrice() * (1 - (percentage / 100));
        product.setPrice(precioFinal);
        productRepository.save(product);

        return modelMapper.map(discount, DiscountDTO.class);
    }


    @Override
    public DiscountDTO updateDiscount(Integer discountId, double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("discountPercentage debe estar entre 0 y 100");
        }
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new IllegalArgumentException("El descuento: " + discountId + " no fue encontrado"));

        discount.setDiscountPercentage(percentage);
        discount = discountRepository.save(discount);
        return modelMapper.map(discount, DiscountDTO.class);
    }


    @Override
    public void deleteDiscount(Integer discountId) {
        if (discountRepository.existsById(discountId)) {
            discountRepository.deleteById(discountId);
        }
        // Si no existe, no hace nada
    }

}

