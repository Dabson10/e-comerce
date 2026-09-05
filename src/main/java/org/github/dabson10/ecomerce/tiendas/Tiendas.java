package org.github.dabson10.ecomerce.tiendas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.ordenes.Ordenes;
import org.github.dabson10.ecomerce.productos.Productos;
import org.github.dabson10.ecomerce.usuarios.Usuarios;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tiendas {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "Ingrese un nombre de una tienda.")
    private String nombre_tienda;
    @Column//Puede recibir null y el rango es con TEXT, no tiene un rango específico.
    private String descripcion;
    @Column(nullable = false)
    @NotNull(message = "Ingrese un valor de estado.")
    private Boolean activo;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
    @Column(nullable = false)
    private OffsetDateTime actualizado_en;
    //Relación con usuarios
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", //Nombre de la columna en esta tabla.
            referencedColumnName = "ID",//Nombre de la ubicación en la otra tabla.
            nullable = false)
    private Usuarios usuario;
    //Relación 1:N con Órdenes
    @OneToMany(mappedBy = "tienda", fetch = FetchType.LAZY)
    private List<Ordenes> ordenes;
    @OneToMany(mappedBy = "tienda", fetch = FetchType.LAZY)
    private List<Productos> productos;

    @PrePersist
    public void cuandoInsertes(){
        this.activo = true;
        this.creado_en = OffsetDateTime.now();
        this.actualizado_en = OffsetDateTime.now();
    }

    @PreUpdate
    public void cuandoActualices(){
        this.actualizado_en = OffsetDateTime.now();
    }


}
