package org.github.dabson10.ecomerce.categorias;

import org.github.dabson10.ecomerce.categorias.dto.CategoriaBasicaDTO;
import org.github.dabson10.ecomerce.categorias.dto.CategoriaSimpleDTO;

import java.util.List;

public interface CategoriasServiceImpl {
    public CategoriaSimpleDTO crearCategoria(String nombre);
    public List<CategoriaBasicaDTO> listarCategorias();
}
