package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    boolean existsByNombre(String nombre);
}
