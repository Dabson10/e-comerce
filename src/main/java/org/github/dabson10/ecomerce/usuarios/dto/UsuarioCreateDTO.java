package org.github.dabson10.ecomerce.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsuarioCreateDTO {
    @NotBlank(message = "Ingrese un nombre")
    private String nombre;
    @NotBlank(message = "Ingrese un correo.")
    @Email(message = "Ingrese un correo valido.")
    private String correo;
    @NotBlank(message = "Ingrese una clave.")
    private String clave;
    @NotBlank(message = "Ingrese un rol")
    private String rol;
}
