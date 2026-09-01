package org.github.dabson10.ecomerce.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.enums.Rol_Usuario;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsuarioCreateDTO {
    @NotBlank(message = "Ingrese un nombre")
    private String nombre;
    @NotBlank(message = "Ingrese un correo.")
    @Email(message = "Ingrese un correo valido.")
    private String correo;
    @NotNull(message = "Ingrese una clave.")
    private String clave;
    private Rol_Usuario rol;
}
