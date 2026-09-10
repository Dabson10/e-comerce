package org.github.dabson10.ecomerce.tiendas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.usuarios.Usuarios;
import org.github.dabson10.ecomerce.usuarios.dto.UsuarioSimpleDTO;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiendaCreateDTO {
    private UUID id;
    @NotBlank(message = "Ingrese un nombre de tienda.")
    private String nombreTienda;
    private String descripcion;//La descripción puede tener null, ya que puede o no tener descripción
    private UUID id_usuario;
}
