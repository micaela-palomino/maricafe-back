package com.uade.tpo.maricafe_back.service;

public interface AdminStatsService {
    Object getOverviewStats();
    Object getProductsByCategory();
    Object getLowStockProducts();
    Object getTopSellingProducts();
    Object getTopSpendingUsers();
    Object getDiscountedProducts();
}
