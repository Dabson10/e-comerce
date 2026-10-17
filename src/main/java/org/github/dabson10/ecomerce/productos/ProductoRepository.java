package org.github.dabson10.ecomerce.productos;

import org.github.dabson10.ecomerce.productos.proyeccion.ProductoProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, UUID> {

    // Agrega la categoria del producto .
    @Query(value = """
            WITH categoriasProducto AS(
                SELECT
                    pr.id, string_agg(cat.nombre, ', ') AS categorias
                FROM productos pr
                    INNER JOIN producto_categoria prC ON pr.id = prC.id_producto
                    INNER JOIN categorias cat ON prC.id_categoria = cat.id
                GROUP BY pr.id
            )
            SELECT
                pr.id AS idProducto, pr.nombre, pr.descripcion, pr.stock, pr.precio, \
                pr.id_tienda AS idTienda, ti.nombre_tienda AS nombreTienda,\s
                des.id AS idDescuento, des.cantidad_descuento AS descuento, \
                des.fecha_inicio AS fechaInicio, des.fecha_fin AS fechaFin, des.activo,
                cp.categorias
            FROM productos pr
                INNER JOIN tiendas ti ON ti.id = pr.id_tienda
                LEFT JOIN descuentos des ON pr.id = des.id_producto
                INNER JOIN categoriasProducto cp ON pr.id = cp.id
            WHERE pr.id = :id AND(
            (NOW() <= des.fecha_fin AND des.fecha_inicio <= NOW()) OR
            ( des.fecha_fin IS NULL AND des.activo = true ))
            ORDER BY fecha_inicio ASC
            LIMIT 1""", nativeQuery = true)
    Optional<ProductoProyeccion> traerProducto(@Param("id") UUID id);

    @Query(value = """
            WITH productosCat AS(
                            SELECT
                                pr.id, string_agg(cat.nombre, ', ') AS categorias
                            FROM productos pr
                                LEFT JOIN producto_categoria prCat ON pr.id = prCat.id_producto
                                LEFT JOIN categorias cat ON prCat.id_categoria = cat.id
                            GROUP BY pr.id
                        ), productoDes AS(
                            SELECT
                                pr.id, pr.nombre, pr.stock, pr.precio,
                                des.cantidad_descuento, des.fecha_inicio, des.fecha_fin, des.activo
                            FROM productos pr
                                LEFT JOIN descuentos des ON pr.id = des.id_producto
                            WHERE des.activo = TRUE
                              AND des.fecha_inicio <= NOW()
                              AND(des.fecha_fin IS NULL OR des.fecha_fin >= NOW())
                        )
                        SELECT
                            ti.id AS idTienda, ti.nombre_tienda AS nombreTienda,
                            pr.id AS idProducto, pr.nombre AS nombre, pr.stock, pr.precio, pr.descripcion,
                            categorias AS categorias,
                            cantidad_descuento AS descuento, fecha_inicio AS fechaInicio,
                            fecha_fin AS fechaFin, prD.activo AS activo, prD.id AS idDescuento
                        FROM tiendas ti
                            INNER JOIN productos pr ON ti.id = pr.id_tienda
                            LEFT JOIN productoDes prD ON pr.id = prD.id
                            LEFT JOIN productosCat prC ON pr.id = prC.id
                        WHERE unaccent(LOWER(prC.categorias)) LIKE unaccent(LOWER(:cat));""", nativeQuery = true)
    List<ProductoProyeccion> filtrarProductosCategoria(@Param("cat") String categoria);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1
                FROM productos pr
                INNER JOIN descuentos des ON pr.id = des.id_producto
                WHERE pr.id = :id AND
                (
                    (NOW() <= des.fecha_fin AND des.fecha_inicio <= NOW()) OR
                    (des.fecha_fin IS NULL AND des.activo)
                )
            )""", nativeQuery = true)
    Boolean existDescuentoProducto(@Param("id") UUID id);
}
