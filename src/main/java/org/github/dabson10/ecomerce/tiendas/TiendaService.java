package org.github.dabson10.ecomerce.tiendas;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.github.dabson10.ecomerce.exception.EntityDuplicateException;
import org.github.dabson10.ecomerce.exception.NotFoundEntityException;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaCreateDTO;
import org.github.dabson10.ecomerce.tiendas.dto.TiendaSimpleDTO;
import org.github.dabson10.ecomerce.usuarios.UsuarioRepository;
import org.github.dabson10.ecomerce.usuarios.Usuarios;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TiendaService implements TiendaServiceImpl{

    private final TiendaRepository tiRe;
    private final TiendaMapper tiMa;
    private final UsuarioRepository usuRe;
    public TiendaService(TiendaRepository tiRe, TiendaMapper tiMa,
                         UsuarioRepository usuRe){
        this.tiRe = tiRe; this.tiMa = tiMa;
        this.usuRe = usuRe;
    }

    /**
     * Esta función es para crear una tienda.
     * Como tal tiene 3 validaciones claras, la primera verifica que no exista otra tienda con ese nombre,
     * la segunda sirve para validar que exista el usuario que se asignó a la tienda. La última es para
     * validar que el usuario no pertenezca a otra tienda.
     * @param tiendaDTO : Datos básicos de la tienda.
     * @return
     */
    @Override
    public TiendaSimpleDTO crearTienda(TiendaCreateDTO tiendaDTO) {
        /* Para realizar la lógica, primero obtendremos un valor de tienda mediante el nombre de esta,
         * esto porque no puede existir otra tienda con el mismo nombre, por lo que si hay una tienda con ese
         * nombre no se podrá realizar la creación de esta.
         */
        if(tiRe.existsByNombreTienda(tiendaDTO.getNombreTienda())){
            //Si la tienda existe entonces regresamos una exceptión
            throw new EntityDuplicateException("Nombre de tienda existente. Ingrese uno diferente.");
        }

        //El usuario no puede estar asociado en una tienda, pero también no puede existir
        // este usuario, por lo que toca validar su existencia.
        if(!usuRe.existsByID(tiendaDTO.getId_usuario())){
            //Si no existe entonces regresamos una exception
            throw new NotFoundEntityException("No se encontró usuario. Ingrese uno existente.");
        }
        //Ahora que sabemos que la tienda no existe toca validar que el usuario
        // que administrara la tienda, no pertenezca a otra.
        if(tiRe.existsByUsuario_ID(tiendaDTO.getId_usuario()) ){
            //Si se encuentra algún usuario entonces regresaremos una exception, porque este ya pertenece a otra tienda
            throw new EntityDuplicateException("Usuario registrado en otra tienda. Ingrese uno diferente.");
        }
        //Ahora que se realizaron validación congruente toca guardar los valores de la tienda.
        Tiendas tienda = tiMa.paraTiendas(tiendaDTO);
        //guardamos un objeto tipo Usuario solo con la referencia del ID.
        tienda.setUsuario(usuRe.getReferenceById(tiendaDTO.getId_usuario()));
        tienda = tiRe.save(tienda);
        System.out.println("El id es: " + tienda.getID());
        return tiMa.paraTiendasSimpleDTO(tienda);
    }

    @Override
    public TiendaSimpleDTO mostrarDatosTienda(UUID ID) {
        //Validamos la existencia de la tienda.
        Tiendas tienda = tiRe.findById(ID)
                .orElseThrow(() -> new NotFoundEntityException("No se encontró tienda con ese ID."));
        //Ahora que tenemos los datos de la tienda, toca plasmarlos en un DTO.
        return tiMa.paraTiendasSimpleDTO(tienda);
    }

    /**
     * Esta función servirá para eliminar o cambiar el estado de activo de la empresa.
     * @param ID : ID de la empresa que se eliminara.
     */
    @Override
    public void eliminarEmpresa(UUID ID) {
        Tiendas tiendas = tiRe.findById(ID).
                orElseThrow(() -> new NotFoundEntityException("No se encontró la empresa. Ingrese una correcta."));
        //Ahora que sabemos cuál es la empresa correcta toca hacer el cambio de estado.
        //Para reutilizar la función si es que se quiere volver activa solo revertiré el estado de la empresa.
        tiendas.setActivo(false);
        tiRe.save(tiendas);
    }
}
