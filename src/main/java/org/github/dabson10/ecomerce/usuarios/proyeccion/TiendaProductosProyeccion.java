package org.github.dabson10.ecomerce.usuarios.proyeccion;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface TiendaProductosProyeccion {
    UUID getIdTienda();
    String getNombreTienda();
    String getDescripcion();
    UUID getIdProducto();
    String getNombreProducto();
    Integer getStock();
    BigDecimal getPrecio();
    String getCategorias();
    Short getCantidadDescuento();
    Instant getFechaInicio();
    Instant getFechaFin();
    Boolean getDescuentoStatus();
}
