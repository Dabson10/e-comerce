package org.github.dabson10.ecomerce.usuarios;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.github.dabson10.ecomerce.enums.Rol_Usuario;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false, length = 70)
    @NotBlank(message = "Ingrese un nombre.")
    private String nombre;
    @Column(nullable = false)
    @NotNull(message = "Ingrese un rol de usuario valido.")
    private Rol_Usuario rol;
    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "Ingrese un corre.")
    @Email(message = "Ingrese un correo valido.")
    private String correo;
    @Column(nullable = false, length = 80)
    @NotBlank(message = "Ingrese una clave.")
    private String clave;
    @Column(nullable = false)
    @NotNull(message = "Ingrese un estado.")
    private Boolean activo;
    @Column(nullable = false)
    @NotNull(message = "Ingrese una fecha de creación.")
    private OffsetDateTime creado_en;
    @Column(nullable = false)
    @NotNull(message = "Ingrese una fecha de actualizado.")
    private OffsetDateTime actualizado_en;
}
