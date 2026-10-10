package org.github.dabson10.ecomerce.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPresentacionDTO {
    private UUID id_producto;
    private String nombre_producto;
    private String categorias;
    private Short cantidad_descuento;
    private Boolean descuento_status;
    private BigDecimal precio;
    private Integer stock;
    private BigDecimal precio_descuento;
    private OffsetDateTime fecha_inicio;
    private OffsetDateTime fecha_fin;
}
