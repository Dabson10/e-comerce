package org.github.dabson10.ecomerce.tiendas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.dabson10.ecomerce.productos.dto.ProductoPresentacionDTO;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TiendaPresentacionDTO {
    private UUID id_tienda;
    private String nombre_tienda;
    private String descripcion;
    private List<ProductoPresentacionDTO> productos;
}
