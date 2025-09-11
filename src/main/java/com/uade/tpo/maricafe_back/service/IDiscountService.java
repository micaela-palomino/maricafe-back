package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;

public interface IDiscountService {
    DiscountDTO createDiscount(Integer productId, double percentage);
    DiscountDTO updateDiscount(Integer discountId, double percentage);
    void deleteDiscount(Integer discountId);
}
