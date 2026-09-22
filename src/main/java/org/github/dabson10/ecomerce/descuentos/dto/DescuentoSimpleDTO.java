package org.github.dabson10.ecomerce.descuentos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DescuentoSimpleDTO {
    private UUID ID;
    private short cantidad_descuento;
    private UUID id_producto;
    private OffsetDateTime fecha_inicio;
    private ProductoSimpleDTO producto;
}
