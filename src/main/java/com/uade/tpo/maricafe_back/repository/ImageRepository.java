package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    // busca por FK product_id
    //List<Image> findByProductProductId(Integer productId);

}