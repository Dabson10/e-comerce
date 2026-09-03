package org.github.dabson10.ecomerce.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.enums.Rol_Usuario;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSimpleDTO {
    private UUID ID;
    @NotBlank(message = "Ingrese un nombre")
    private String nombre;
    @NotBlank(message = "Ingrese un correo.")
    @Email(message = "Ingrese un correo valido.")
    private String correo;
//    @NotNull(message = "Ingrese un rol")
    private Rol_Usuario rol;
    private OffsetDateTime creado_en;
    private OffsetDateTime actualizado_en;
}
