package org.github.dabson10.ecomerce.descuentos;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.github.dabson10.ecomerce.productos.Productos;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Descuentos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false)
    @Min(0)@Max(100)
    private Integer cantidad_descuento;
    @Column
    private OffsetDateTime fecha_inicio;
    @Column
    private OffsetDateTime fecha_fin;
    //Relación con productos.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Productos producto;
}
