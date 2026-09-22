package org.github.dabson10.ecomerce.descuentos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.productos.Productos;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Descuentos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false)
    @Min(0)@Max(100)
    private Short cantidad_descuento;
    @Column
    private OffsetDateTime fecha_inicio;
    @Column
    private OffsetDateTime fecha_fin;
    @Column
    private Boolean activo;
    //Relación con productos.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Productos producto;

    @PrePersist
    public void cuandoCreas(){
        this.fecha_inicio = OffsetDateTime.now();
        this.activo = true;
    }
}
