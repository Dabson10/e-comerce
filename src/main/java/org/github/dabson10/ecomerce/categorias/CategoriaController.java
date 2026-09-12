package org.github.dabson10.ecomerce.categorias;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.github.dabson10.ecomerce.categorias.dto.CategoriaSimpleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Validated
public class CategoriaController {

    private final CategoriasService caSe;

    public CategoriaController(CategoriasService caSe){
        this.caSe = caSe;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoriaSimpleDTO> crearCategoria(
            @RequestParam(name = "nombre") @NotBlank(message = "Ingrese un nombre de categoria.")
            String nombre
    ){
        CategoriaSimpleDTO categoria = caSe.crearCategoria(nombre);
        return new ResponseEntity<>(categoria, HttpStatus.CREATED);
    }


}
