package org.github.dabson10.ecomerce.productos.proyeccion;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface ProductoProyeccion {
    UUID getIdProducto();
    String getNombre();
    String getCategorias();
    String getDescripcion();
    Integer getStock();
    BigDecimal getPrecio();
    UUID getIdTienda();
    String getNombreTienda();
    UUID getIdDescuento();
    Short getDescuento(); //Cantidad del descuento.
    Instant getFechaInicio();
    Instant getFechaFin();
    Boolean getActivo();
}
