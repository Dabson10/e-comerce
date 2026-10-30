package org.github.dabson10.ecomerce.config;

import org.github.dabson10.ecomerce.ExceptionGlobal.CustomAuthenticationEntryPoint;
import org.github.dabson10.ecomerce.usuarios.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigSecurity {

    /**
     * La siguiente función es la configuración para la seguridad de los endpoints.<br><br>
     * 1.csrf(c.disabled): Cross Site Request Forgery. Busca proteger la integridad de las apis,<br>
     *  buscando proteger de otros navegadores realicen acciones con los roles. Lo desactivamos porque usamos JWT.<br><br>
     * 2.authorizeHttpRequest(http->
     *  http.requestMatchers("/users/create","/users/reed").permitAll() //Está diciendo que todos pueden entrar en estas dos rutas
     *       //estando o no autenticados.
     *       .anyRequest().authenticated()//Las rutas no escritas arriba ahora serán accesibles si el usuario está autenticado.
     *  )<br><br>
     * 3.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPlicy.STATELESS)) //Le decimos al servidor que no guarde sessiones
     *      //, ya que utilizaremos JWT para mantener sesiones mediante tokens. Le pide que solamente valide.<br><br>
     * 4.formLogin(form -> from.disable()).httpBasic(http -> http.disable())//En este caso son dos funcionalidades descativadas.<br>
     *  //formLogin:Por lo general esta activado y al llamar un endpoint muestra una pagina web pidiendo credenciales,
     *      //lo desactivamos porque usaremos JWT para validar accesos.<br>
     *  //httpBasic:Es la ventana emergente en donde se pide usuario y contraseña de seguridad.<br><br>
     * 5.addFilterBefore(new JwtFilter(usuarioRepository), UsernamePasswordAuthenticationFilter.class)
     *  //Antes que Spring aplique un filtro, nosotros implementaremos el nuestro utilizando JwtFilter, y despues
     *  //promoviendo el filtro de autenticidad.
     *
     * @param security : Builder de la clase HttpSecurity para encadenar configuración como se requiera.
     * @param usuarioRepository :Repository del usuario, para JwtFilter
     * @return
     */
    @Bean
    public SecurityFilterChain configuracion(HttpSecurity security, UsuarioRepository usuarioRepository,
                                             CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        return security
                .csrf(c-> c.disable())
                .authorizeHttpRequests(http ->
                        http.requestMatchers("/users/create","/users/reed").permitAll()
                                .requestMatchers("/product/**").hasRole("Empresa")
                                .requestMatchers("/users/").hasRole("Cliente")
                                .anyRequest().authenticated() )
                .sessionManagement( session ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .formLogin(form -> form.disable())
                .httpBasic(http -> http.disable())
                //Capturamos las exceptions de token incorrecto y sin autenticación.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAuthenticationEntryPoint))
                .addFilterBefore(new JwtFilter(usuarioRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
