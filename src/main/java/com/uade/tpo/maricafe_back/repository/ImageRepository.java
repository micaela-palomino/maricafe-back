package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    // busca por FK product_id
    List<Image> findByProductId(Long productId);
}