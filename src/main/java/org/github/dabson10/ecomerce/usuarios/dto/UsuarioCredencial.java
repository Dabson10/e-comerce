package org.github.dabson10.ecomerce.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UsuarioCredencial {
    @NotBlank(message = "Ingrese un correo.")
    @Email(message = "Ingrese un correo valido.")
    private String correo;
    @NotBlank(message = "Ingrese una contraseña.")
    private String clave;
}
