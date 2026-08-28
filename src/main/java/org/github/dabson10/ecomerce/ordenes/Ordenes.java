package org.github.dabson10.ecomerce.ordenes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.github.dabson10.ecomerce.detalle_ordenes.DetalleOrdenes;
import org.github.dabson10.ecomerce.enums.Estado_Orden;
import org.github.dabson10.ecomerce.tiendas.Tiendas;
import org.github.dabson10.ecomerce.usuarios.Usuarios;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Ordenes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado_Orden estado;
    @Column(nullable = false, precision = 12, scale = 2)//Rango para NUMERIC(12,2)
    @PositiveOrZero
    private BigDecimal total;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
    /*Para relaciones 1:N es necesario poner una lista, esta estara en la parte de los 1 osea
    * 1 usuario tiene muchas ordenes.*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tiendas tienda;
    @OneToMany(mappedBy = "orden", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,})
    private List<DetalleOrdenes> detalles;
}
