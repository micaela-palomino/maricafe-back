package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Producto findByTitulo(String titulo);
}
