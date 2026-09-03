package org.github.dabson10.ecomerce.usuarios;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.enums.Rol_Usuario;
import org.github.dabson10.ecomerce.ordenes.Ordenes;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

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
    @JdbcType(PostgreSQLEnumJdbcType.class)
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

    /**
     * Esta función tipo {@code @PrePersist}, se ejecuta un poco antes de
     * realizar un INSERT o save() en base de datos.
     */
    @PrePersist
    public void crearUsuario(){
        this.activo = true;
        this.creado_en = OffsetDateTime.now();
        this.actualizado_en = OffsetDateTime.now();
    }

    /**
     * Esta función tipo {@code @PreUpdate}, se ejecuta un poco antes de
     * realizar un UPDATE o save en base de datos, no pensar que la función
     * save solo sirve para guardar, esta se suele utilizar tambien para
     * actualizar.
     */
    @PreUpdate
    public void actualizarDatos(){
        this.actualizado_en = OffsetDateTime.now();
    }
}
