package org.github.dabson10.ecomerce.usuarios;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.enums.Rol_Usuario;
import org.github.dabson10.ecomerce.ordenes.Ordenes;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false, length = 70)
    @NotBlank(message = "Ingrese un nombre.")
    private String nombre;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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
    private Boolean activo;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
    @Column(nullable = false)
    private OffsetDateTime actualizado_en;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Ordenes> ordenes;
}
