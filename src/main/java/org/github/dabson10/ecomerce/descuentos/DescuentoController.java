package org.github.dabson10.ecomerce.descuentos;

import jakarta.validation.Valid;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCompletoDTO;
import org.github.dabson10.ecomerce.descuentos.dto.DescuentoCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/discount")
public class DescuentoController {

    private final DescuentoService deSe;

    public DescuentoController(DescuentoService deSe){
        this.deSe = deSe;
    }


    @PostMapping("/create")
    public ResponseEntity<DescuentoCompletoDTO> crearDescuento(
            @Valid @RequestBody DescuentoCreateDTO descuentoDTO){
        DescuentoCompletoDTO descuento = deSe.crearDescuento(descuentoDTO);
        return new ResponseEntity<>(descuento, HttpStatus.CREATED);
    }

    /**
     * Controller para eliminar o desactivar un descuento.
     * @param ID
     * @return
     */
    @PatchMapping("/delete/{ID}")
    public ResponseEntity<DescuentoCompletoDTO> eliminarDescuento(
            @PathVariable(name = "ID")UUID ID
            ){
        DescuentoCompletoDTO descuento = deSe.desactivarDescuento(ID);
        return new ResponseEntity<>(descuento, HttpStatus.ACCEPTED);
    }

}
