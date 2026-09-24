package org.github.dabson10.ecomerce.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoSimpleDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoMostrarDTO {
    private UUID ID;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private BigDecimal precioDescuento;
    List<DescuentoSimpleDTO> descuentos;
}
