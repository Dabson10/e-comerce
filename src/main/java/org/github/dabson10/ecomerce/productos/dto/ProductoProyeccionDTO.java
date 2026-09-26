package org.github.dabson10.ecomerce.productos.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface ProductoProyeccionDTO {
    UUID getIdProducto();
    String getNombre();
    String getDescripcion();
    Integer getStock();
    BigDecimal getPrecio();
    UUID getIdTienda();
    UUID getIdDescuento();
    Short getDescuento(); //Cantidad del descuento.
    Instant getFechaInicio();
    Instant getFechaFin();
    Boolean getActivo();
}
