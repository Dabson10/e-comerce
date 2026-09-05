package org.github.dabson10.ecomerce.tiendas;

import org.github.dabson10.ecomerce.tiendas.dto.TiendaCreateDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaSimpleDTO;

import java.util.UUID;

public interface TiendaServiceImpl {

     TiendaSimpleDTO crearTienda(TiendaCreateDTO tienda);
     TiendaSimpleDTO mostrarDatosTienda(UUID ID);
     void eliminarEmpresa(UUID ID);
}
