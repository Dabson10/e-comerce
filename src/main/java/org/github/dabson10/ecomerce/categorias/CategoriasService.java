package org.github.dabson10.ecomerce.categorias;

import org.github.dabson10.ecomerce.categorias.dto.CategoriaSimpleDTO;
import org.springframework.stereotype.Service;

@Service
public class CategoriasService implements CategoriasServiceImpl{

    private final CategoriasRepository caRe;
    private final CategoriaMapper caMa;
    public CategoriasService(CategoriasRepository caRe, CategoriaMapper caMa){
        this.caRe = caRe; this.caMa = caMa;
    }

    @Override
    public CategoriaSimpleDTO crearCategoria(String nombre) {
        Categorias categorias = new Categorias();
        categorias.setNombre(nombre);
        categorias = caRe.save(categorias);
        return caMa.paraCategoriasSimpleDTO(categorias);
    }
}
