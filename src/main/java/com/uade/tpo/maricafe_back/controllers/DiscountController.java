package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.CreateUpdateDiscountDTO;
import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
import com.uade.tpo.maricafe_back.service.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("discounts")
public class DiscountController {

    private final IProductService productService;

    public DiscountController(IProductService productService) {
        this.productService = productService;
    }

    //EP2 TOMI: 3.6 POST /products/{id}/discounts
    @PostMapping("/{productId}")
    public ResponseEntity<DiscountDTO> createDiscount(
            @PathVariable Integer productId,
            @RequestBody CreateUpdateDiscountDTO dto) {
        DiscountDTO saved = productService.createDiscount(productId, dto.getDiscountPercentage());
        return ResponseEntity.created(URI.create("/discounts/" + saved.getDiscountId())).body(saved);
    }

    //EP3 TOMI: 3.7 PATCH /discounts/{discountId}
    @PatchMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> updateDiscount(
            @PathVariable Integer discountId,
            @RequestBody CreateUpdateDiscountDTO dto) {
        return ResponseEntity.ok(productService.updateDiscount(discountId, dto.getDiscountPercentage()));
    }
}
