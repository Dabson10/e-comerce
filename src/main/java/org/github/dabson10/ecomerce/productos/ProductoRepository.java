package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.dto.ProductoMostrarDTO;
import org.github.dabson10.ecomerce.productos.dto.ProductoProyeccionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, UUID> {
    //Consulta que traerá un producto por su ID y filtrará los descuentos que tiene.
    //ARREGLAR ESTA CONSULTA.
    @Query(value = "SELECT\n" +
            "    pr.id AS IdProducto, pr.nombre, pr.descripcion, pr.stock, pr.precio, " +
            "    pr.id_tienda AS IdTiendagi,\n" +
            "    des.id AS IdDescuento, des.cantidad_descuento AS descuento, " +
            "    des.fecha_inicio AS fechaInicio, des.fecha_fin AS fechaFin, des.activo\n" +
            "FROM productos pr\n" +
            "LEFT JOIN descuentos des ON pr.id = des.id_producto\n" +
            "WHERE pr.id = :id AND(\n" +
            "(NOW() <= des.fecha_fin AND des.fecha_inicio <= NOW()) OR\n" +
            "( des.fecha_fin IS NULL AND des.activo = true ))\n" +
            "ORDER BY fecha_inicio ASC\n" +
            "LIMIT 1", nativeQuery = true)
    Optional<ProductoProyeccionDTO> traerProducto(@Param("id") UUID id);

    @Query(value = "SELECT EXISTS(\n" +
            "    SELECT 1\n" +
            "    FROM productos pr\n" +
            "    INNER JOIN descuentos des ON pr.id = des.id_producto\n" +
            "    WHERE pr.id = :id AND\n" +
            "    (\n" +
            "        (NOW() <= des.fecha_fin AND des.fecha_inicio <= NOW()) OR\n" +
            "        (des.fecha_fin IS NULL AND des.activo)\n" +
            "    )\n" +
            ")", nativeQuery = true)
    Boolean existDescuentoProducto(@Param("id") UUID id);
}
