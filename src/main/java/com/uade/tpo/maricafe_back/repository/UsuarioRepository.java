package com.uade.tpo.maricafe_back.repository;

import com.uade.tpo.maricafe_back.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByMail(String mail);
    Usuario findByNombreUsuario(String nombreUsuario);
}
