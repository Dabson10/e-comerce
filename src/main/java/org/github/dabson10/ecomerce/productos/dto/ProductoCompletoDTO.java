package org.github.dabson10.ecomerce.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.categorias.dto.CategoriaSimpleDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCompletoDTO {
    private UUID ID;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;
    private UUID id_tienda;
    private List<CategoriaSimpleDTO> categorias;
}
