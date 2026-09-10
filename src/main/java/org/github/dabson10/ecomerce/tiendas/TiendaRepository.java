package org.github.dabson10.ecomerce.tiendas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TiendaRepository extends JpaRepository<Tiendas, UUID> {
    //Esta consulta confirmará la existencia de un usuario en una tienda mediante él id del usuario.
    boolean existsByUsuario_ID(UUID usuarioID);
    //Validá que la exista una tienda con ID.
    boolean existsTiendasByID(UUID id);
    //Validá que no exista una tienda con el mismo nombre
    boolean existsByNombreTienda(String nombre_tienda);
}
