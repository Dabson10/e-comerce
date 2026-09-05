package org.github.dabson10.ecomerce.tiendas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiendaSimpleDTO {
    private UUID ID;
    private String nombre_tienda;
    private String descripcion;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;
    private UUID id_usuario;
}
