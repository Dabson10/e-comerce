package org.github.dabson10.ecomerce.categorias;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.productos.Productos;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ID;
    @Column(nullable = false, length = 80)
    private String nombre;
    @Column(nullable = false)
    private OffsetDateTime creado_en;
    @Column(nullable = false)
    private OffsetDateTime actualizado_en;
    //Relación N:N de productos a categorias intermedia tipo producto_categoria
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    private List<Productos> productos;

    @PrePersist
    public void paraCrear(){
        this.creado_en = OffsetDateTime.now();
        this.actualizado_en = OffsetDateTime.now();
    }

}
