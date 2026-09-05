package org.github.dabson10.ecomerce.tiendas;

import jakarta.validation.Valid;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaCreateDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaSimpleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/store")
public class TiendasController {

    private final TiendaService tiSe;

    public TiendasController(TiendaService tiSe){
        this.tiSe = tiSe;
    }

    @PostMapping("/create")
    public ResponseEntity<TiendaSimpleDTO> crearTienda(
             @RequestBody TiendaCreateDTO tiendaD
            ){
        TiendaSimpleDTO tienda = tiSe.crearTienda(tiendaD);
        return new ResponseEntity<>(tienda, HttpStatus.CREATED);
    }

    @PatchMapping("/status/{ID}")
    public ResponseEntity<Map<String, String>> cambiarEstadoTienda(
            @PathVariable UUID ID
    ){
        tiSe.eliminarEmpresa(ID);
        return new ResponseEntity<>(Map.of("Cambio de estado:","Se cambio el estado de la tienda."), HttpStatus.NO_CONTENT);
    }
}
