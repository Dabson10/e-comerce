package org.github.dabson10.ecomerce.categorias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.productos.dto.ProductoSimpleDTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaCompletaDTO {
    private UUID ID;
    private String nombre;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;
    private List<ProductoSimpleDTO> productos;
}
