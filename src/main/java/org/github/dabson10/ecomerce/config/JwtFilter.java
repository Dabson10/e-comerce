package org.github.dabson10.ecomerce.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.github.dabson10.ecomerce.usuarios.UsuarioRepository;
import org.github.dabson10.ecomerce.usuarios.Usuarios;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
            return;
        }
        //De la cadena quitamos la frase "Bearer "
        String token = header.substring(7);

        if(!JwtUtil.validateToken(token)){
            //Si el token fue modificado, su firma está rota o el token vacío entonces continuamos sin autenticar.
            filterChain.doFilter(request, response);
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null){
            //Del token obtenemos el subject en este caso lo que pasamos que es el correo.
            String correo = JwtUtil.getSubjectFromToken(token);
            Optional<Usuarios> usuario = usuRe.findByCorreo(correo);
            if(usuario.isPresent()){

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(correo, null, List.of());

                //Autenticamos el usuario.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
