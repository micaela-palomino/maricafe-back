package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
