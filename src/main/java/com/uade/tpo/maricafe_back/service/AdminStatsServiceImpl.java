package com.uade.tpo.maricafe_back.service;

import com.uade.tpo.maricafe_back.entity.Discount;
import com.uade.tpo.maricafe_back.entity.Order;
import com.uade.tpo.maricafe_back.entity.Product;
import com.uade.tpo.maricafe_back.repository.DiscountRepository;
import com.uade.tpo.maricafe_back.repository.OrderRepository;
import com.uade.tpo.maricafe_back.repository.ProductRepository;
import com.uade.tpo.maricafe_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminStatsServiceImpl implements AdminStatsService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;

    @Override
    public Object getOverviewStats() {
        long totalProducts = productRepository.count();
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        long totalDiscounts = discountRepository.count();
        
        // Calculate total revenue from active orders
        List<Order> activeOrders = orderRepository.findByActiveTrue();
        double totalRevenue = activeOrders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", totalProducts);
        stats.put("totalUsers", totalUsers);
        stats.put("totalOrders", totalOrders);
        stats.put("totalDiscounts", totalDiscounts);
        stats.put("totalRevenue", totalRevenue);
        
        return stats;
    }

    @Override
    public Object getProductsByCategory() {
        List<Product> allProducts = productRepository.findAll();
        
        Map<String, Long> productsByCategory = allProducts.stream()
                .collect(Collectors.groupingBy(
                    product -> product.getCategory() != null ? product.getCategory().getName() : "Sin categoría",
                    Collectors.counting()
                ));

        return productsByCategory;
    }

    @Override
    public Object getLowStockProducts() {
        // Products with stock <= 5
        List<Product> lowStockProducts = productRepository.findAll().stream()
                .filter(product -> product.getStock() <= 5)
                .collect(Collectors.toList());

        return lowStockProducts.stream()
                .map(product -> {
                    Map<String, Object> productInfo = new HashMap<>();
                    productInfo.put("id", product.getProductId());
                    productInfo.put("title", product.getTitle());
                    productInfo.put("stock", product.getStock());
                    productInfo.put("price", product.getPrice());
                    productInfo.put("category", product.getCategory() != null ? product.getCategory().getName() : "Sin categoría");
                    return productInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Object getTopSellingProducts() {
        List<Order> activeOrders = orderRepository.findByActiveTrue();
        
        // Count total quantity sold for each product
        Map<Integer, Integer> productSales = new HashMap<>();
        Map<Integer, String> productNames = new HashMap<>();
        Map<Integer, Double> productPrices = new HashMap<>();
        
        for (Order order : activeOrders) {
            if (order.getOrderItems() != null) {
                for (var orderItem : order.getOrderItems()) {
                    Integer productId = orderItem.getProduct().getProductId();
                    String productName = orderItem.getProduct().getTitle();
                    Double productPrice = orderItem.getProduct().getPrice();
                    
                    productSales.merge(productId, orderItem.getQuantity(), Integer::sum);
                    productNames.put(productId, productName);
                    productPrices.put(productId, productPrice);
                }
            }
        }
        
        // Sort by total quantity sold (descending) and return top 10
        return productSales.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .map(entry -> {
                    Map<String, Object> productStats = new HashMap<>();
                    productStats.put("productId", entry.getKey());
                    productStats.put("productName", productNames.get(entry.getKey()));
                    productStats.put("totalSold", entry.getValue());
                    productStats.put("price", productPrices.get(entry.getKey()));
                    return productStats;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Object getTopSpendingUsers() {
        List<Order> activeOrders = orderRepository.findByActiveTrue();
        
        // Calculate total spending per user
        Map<Integer, Double> userSpending = new HashMap<>();
        Map<Integer, String> userNames = new HashMap<>();
        Map<Integer, String> userEmails = new HashMap<>();
        
        for (Order order : activeOrders) {
            if (order.getUser() != null) {
                Integer userId = order.getUser().getUserId();
                String userName = order.getUser().getFirstName() + " " + order.getUser().getLastName();
                String userEmail = order.getUser().getEmail();
                
                userSpending.merge(userId, order.getTotalPrice(), Double::sum);
                userNames.put(userId, userName);
                userEmails.put(userId, userEmail);
            }
        }
        
        // Sort by total spending (descending) and return top 10
        return userSpending.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .map(entry -> {
                    Map<String, Object> userStats = new HashMap<>();
                    userStats.put("userId", entry.getKey());
                    userStats.put("userName", userNames.get(entry.getKey()));
                    userStats.put("userEmail", userEmails.get(entry.getKey()));
                    userStats.put("totalSpent", entry.getValue());
                    return userStats;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Object getDiscountedProducts() {
        List<Discount> allDiscounts = discountRepository.findAll();
        
        return allDiscounts.stream()
                .map(discount -> {
                    Product product = discount.getProduct();
                    double originalPrice = product.getPrice();
                    double discountAmount = originalPrice * (discount.getDiscountPercentage() / 100.0);
                    double discountedPrice = originalPrice - discountAmount;
                    
                    Map<String, Object> productInfo = new HashMap<>();
                    productInfo.put("discountId", discount.getDiscountId());
                    productInfo.put("productId", product.getProductId());
                    productInfo.put("productName", product.getTitle());
                    productInfo.put("originalPrice", originalPrice);
                    productInfo.put("discountPercentage", discount.getDiscountPercentage());
                    productInfo.put("discountedPrice", discountedPrice);
                    productInfo.put("savings", discountAmount);
                    productInfo.put("category", product.getCategory() != null ? product.getCategory().getName() : "Sin categoría");
                    productInfo.put("stock", product.getStock());
                    return productInfo;
                })
                .collect(Collectors.toList());
    }
}
