package org.github.dabson10.ecomerce.categorias;

import org.github.dabson10.ecomerce.categorias.dto.CategoriaBasicaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, UUID> {
    boolean existsByID(UUID id);
    @Query(value = """
            SELECT EXISTS(
              SELECT
                  1
              FROM categorias cat WHERE unaccent(LOWER(cat.nombre)) = unaccent(LOWER('Óleos'))
            );""", nativeQuery = true)
    boolean existeCategoria(String categoria);

    @Query(value = "SELECT DISTINCT cat.nombre, cat.id FROM categorias cat ;",
            nativeQuery = true)
    List<CategoriaBasicaDTO> listarCategorias();
}
