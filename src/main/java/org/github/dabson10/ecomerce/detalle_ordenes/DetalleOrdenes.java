package org.github.dabson10.ecomerce.detalle_ordenes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.github.dabson10.ecomerce.ordenes.Ordenes;
import org.github.dabson10.ecomerce.productos.Productos;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class DetalleOrdenes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false)
    @Positive
    private Integer cantidad;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio_unitario;
    //Relación con producto y ordenes.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden")
    private Ordenes orden;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Productos producto;
}
