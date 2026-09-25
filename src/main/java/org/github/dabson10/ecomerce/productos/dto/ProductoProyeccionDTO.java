package org.github.dabson10.ecomerce.productos.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface ProductoProyeccionDTO {
    UUID getIdProducto();
    String getNombre();
    String getDescripcion();
    Integer getStock();
    BigDecimal getPrecio();
    UUID getIdDescuento();
    Short getDescuento();
    OffsetDateTime getFechaInicio();
    OffsetDateTime getFechaFin();
    Boolean getActivo();
}
