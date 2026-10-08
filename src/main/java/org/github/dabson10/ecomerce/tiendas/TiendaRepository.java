package org.github.dabson10.ecomerce.tiendas;

import org.github.dabson10.ecomerce.usuarios.proyeccion.TiendaProductosProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TiendaRepository extends JpaRepository<Tiendas, UUID> {
    //Esta consulta confirmará la existencia de un usuario en una tienda mediante él id del usuario.
    boolean existsByUsuario_ID(UUID usuarioID);
    //Válida que la exista una tienda con ID.
    boolean existsTiendasByID(UUID id);
    //Válida que no exista una tienda con el mismo nombre
    boolean existsByNombreTienda(String nombre_tienda);
    Boolean getActivoByID(UUID id);
    //Consulta para traer los productos, categorías y descuentos de una tienda.
    @Query(value = "WITH categoriasProductos AS(\n" +
            "    SELECT\n" +
            "        pr.id AS id_productoC, string_agg(cat.nombre, ', ') AS categorias\n" +
            "    FROM productos pr\n" +
            "        LEFT JOIN producto_categoria prCat ON pr.id = prCat.id_producto\n" +
            "        LEFT JOIN categorias cat ON prCat.id_categoria = cat.id\n" +
            "    WHERE pr.id_tienda = :ID AND pr.activo = TRUE\n" +
            "    GROUP BY pr.id\n" +
            "), descuentosProductos AS(\n" +
            "    SELECT\n" +
            "        pr.id,\n" +
            "        des.cantidad_descuento, des.fecha_inicio, des.fecha_fin, des.activo\n" +
            "    FROM productos pr\n" +
            "             LEFT JOIN descuentos des ON pr.id = des.id_producto\n" +
            "    WHERE pr.id_tienda = :ID\n" +
            "      AND des.activo = TRUE\n" +
            "      AND des.fecha_inicio <= NOW()\n" +
            "    AND(des.fecha_fin IS NULL OR des.fecha_fin >= NOW())\n" +
            ")SELECT\n" +
            "     ti.id AS idTienda, ti.nombre_tienda AS nombreTienda, ti.descripcion,\n" +
            "     pr.id AS idProducto, pr.nombre AS nombreProducto, pr.stock, pr.precio,\n" +
            "     categorias,\n" +
            "     cantidad_descuento AS cantidadDescuento, fecha_inicio AS fechaInicio,\n" +
            "     fecha_fin AS fechaFin, dp.activo AS descuentoStatus\n" +
            "FROM tiendas ti\n" +
            "INNER JOIN productos pr ON ti.id = pr.id_tienda\n" +
            "INNER JOIN categoriasProductos cp ON  pr.id = cp.id_productoC\n" +
            "LEFT JOIN descuentosProductos dp ON pr.id = dp.id\n" +
            "WHERE ti.id = :ID\n" +
            "AND pr.activo = TRUE;", nativeQuery = true)
    List<TiendaProductosProyeccion> traerProductosTienda(@Param("ID") UUID ID);

}
