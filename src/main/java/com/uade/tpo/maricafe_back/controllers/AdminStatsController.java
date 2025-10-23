package com.uade.tpo.maricafe_back.controllers;

import com.uade.tpo.maricafe_back.entity.dto.ApiResponseDTO;
import com.uade.tpo.maricafe_back.service.AdminStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminStatsController {

    private final AdminStatsService adminStatsService;

    @GetMapping("/overview")
    public ResponseEntity<ApiResponseDTO<Object>> getOverviewStats() {
        return ResponseEntity.ok(ApiResponseDTO.success("Estadísticas generales obtenidas", adminStatsService.getOverviewStats()));
    }

    @GetMapping("/products-by-category")
    public ResponseEntity<ApiResponseDTO<Object>> getProductsByCategory() {
        return ResponseEntity.ok(ApiResponseDTO.success("Productos por categoría obtenidos", adminStatsService.getProductsByCategory()));
    }

    @GetMapping("/low-stock-products")
    public ResponseEntity<ApiResponseDTO<Object>> getLowStockProducts() {
        return ResponseEntity.ok(ApiResponseDTO.success("Productos con stock bajo obtenidos", adminStatsService.getLowStockProducts()));
    }

    @GetMapping("/top-selling-products")
    public ResponseEntity<ApiResponseDTO<Object>> getTopSellingProducts() {
        return ResponseEntity.ok(ApiResponseDTO.success("Productos más vendidos obtenidos", adminStatsService.getTopSellingProducts()));
    }

    @GetMapping("/top-spending-users")
    public ResponseEntity<ApiResponseDTO<Object>> getTopSpendingUsers() {
        return ResponseEntity.ok(ApiResponseDTO.success("Usuarios que más gastan obtenidos", adminStatsService.getTopSpendingUsers()));
    }

    @GetMapping("/discounted-products")
    public ResponseEntity<ApiResponseDTO<Object>> getDiscountedProducts() {
        return ResponseEntity.ok(ApiResponseDTO.success("Productos con descuento obtenidos", adminStatsService.getDiscountedProducts()));
    }
}
