package org.github.dabson10.ecomerce.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.github.dabson10.ecomerce.usuarios.UsuarioRepository;
import org.github.dabson10.ecomerce.usuarios.Usuarios;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuRe;

    public JwtFilter(UsuarioRepository usuRe){
        this.usuRe = usuRe;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        //Obtenemos la cabecera del token
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            //Si el header es nulo o no empieza con "Bearer " continuamos sin autenticar.
            filterChain.doFilter(request, response);
            //No podrá acceder a url que requieran autenticación. Solamente los que no tienen autenticación.
            return;
        }
        //De la cadena quitamos la frase "Bearer "
        String token = header.substring(7);
        //Valida el token y su autenticidad.
        if(!JwtUtil.validateToken(token)){
            //Si el token fue modificado, su firma está rota o el token vacío entonces continuamos sin autenticar.
            filterChain.doFilter(request, response);
            //No podrá acceder a url que requieran autenticación. Solamente los que no tienen autenticación.
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null){
            //Del token obtenemos el subject en este caso lo que pasamos que es el correo.
            String correo = JwtUtil.getSubjectFromToken(token);
            Optional<Usuarios> usuario = usuRe.findByCorreo(correo);
            //Validamos que el usuario exista.
            if(usuario.isPresent()){
                //Si el usuario existe entonces lo validamos.
                //Como existe el usuario ahora buscaremos el rol del usuario para asignarselo.
                String rol = JwtUtil.getClaimFromToken(token, "rol");
                //Hacemos una lista con los datos que ocupara el usuario.
                List<GrantedAuthority> autoridades = List.of(new SimpleGrantedAuthority(rol));

                //Ahora al correo le decimos que está autenticado y que tiene ciertas autorizaciones, mediante su rol.
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(correo, null, autoridades);
                //Autenticamos al usuario y le pasamos una lista de roles, inicialmente vacía.

                //Ahora el usuario esta autenticado pero debemos darle algo que identifique al usuario para no
                //repetir todo el proceso, como un brazalete de acceso.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
