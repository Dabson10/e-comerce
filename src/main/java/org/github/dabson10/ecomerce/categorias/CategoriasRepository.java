package org.github.dabson10.ecomerce.categorias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, UUID> {
}
