package org.github.dabson10.ecomerce.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, UUID> {
    boolean existsByCorreo(String correo);
    Optional<Usuarios> findByCorreo(String correo);
}
