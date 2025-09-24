package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.ApiResponseDTO;
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
    public ResponseEntity<ApiResponseDTO<DiscountDTO>> createDiscount(
            @PathVariable Integer productId,
            @RequestBody CreateUpdateDiscountDTO dto) {
        DiscountDTO saved = discountService.createDiscount(productId, dto.getDiscountPercentage());
        return ResponseEntity.created(URI.create("/discounts/" + saved.getDiscountId()))
                .body(ApiResponseDTO.success("Descuento creado con éxito", saved));
    }

    //EP3: 3.7 PATCH /discounts/{discountId}
    @PatchMapping("/{discountId}")
    public ResponseEntity<ApiResponseDTO<DiscountDTO>> updateDiscount(
            @PathVariable Integer discountId,
            @RequestBody CreateUpdateDiscountDTO dto) {
        DiscountDTO updated = discountService.updateDiscount(discountId, dto.getDiscountPercentage());
        return ResponseEntity.ok(ApiResponseDTO.success("Descuento actualizado con éxito", updated));
    }

    //EP1: eliminar descuento
    @DeleteMapping("/{discountId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteDiscount(@PathVariable Integer discountId) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.ok(ApiResponseDTO.success("Descuento eliminado con éxito"));
    }
}
