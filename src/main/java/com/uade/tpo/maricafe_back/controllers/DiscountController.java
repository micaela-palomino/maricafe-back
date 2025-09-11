package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.CreateUpdateDiscountDTO;
import com.uade.tpo.maricafe_back.entity.dto.DiscountDTO;
import com.uade.tpo.maricafe_back.service.IDiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("discounts")
public class DiscountController {

    private final IDiscountService discountService;

    public DiscountController(IDiscountService discountService) {
        this.discountService = discountService;
    }

    //EP2: 3.6 POST /products/{id}/discounts
    @PostMapping("/{productId}")
    public ResponseEntity<DiscountDTO> createDiscount(
            @PathVariable Integer productId,
            @RequestBody CreateUpdateDiscountDTO dto) {
        DiscountDTO saved = discountService.createDiscount(productId, dto.getDiscountPercentage());
        return ResponseEntity.created(URI.create("/discounts/" + saved.getDiscountId())).body(saved);
    }

    //EP3: 3.7 PATCH /discounts/{discountId}
    @PatchMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> updateDiscount(
            @PathVariable Integer discountId,
            @RequestBody CreateUpdateDiscountDTO dto) {
        return ResponseEntity.ok(discountService.updateDiscount(discountId, dto.getDiscountPercentage()));
    }

    //EP1: eliminar descuento
    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer discountId) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }
}
