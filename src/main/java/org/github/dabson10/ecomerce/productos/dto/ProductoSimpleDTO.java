package org.github.dabson10.ecomerce.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSimpleDTO {
    private UUID ID;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;
    private UUID id_tienda;


}
