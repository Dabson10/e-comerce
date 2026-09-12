package org.github.dabson10.ecomerce.categorias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaSimpleDTO {
    private UUID ID;
    private String nombre;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;
}
