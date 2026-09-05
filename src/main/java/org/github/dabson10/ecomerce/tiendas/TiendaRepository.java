package org.github.dabson10.ecomerce.tiendas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TiendaRepository extends JpaRepository<Tiendas, UUID> {
    //Esta consulta buscará en BD de tiendas solo la coincidencia por nombre_tienda y lo guardará en un Optional.
    @Query(value = "SELECT t.* FROM tiendas t WHERE t.nombre_tienda = :nombre", nativeQuery = true)
    Optional<Tiendas> buscarTiendaPorNombre(@Param("nombre") String nombre);

    //Esta consulta confirmará la existencia de un usuario en una tienda mediante él id del usuario.
    boolean existsByUsuario_ID(UUID usuarioID);
}
