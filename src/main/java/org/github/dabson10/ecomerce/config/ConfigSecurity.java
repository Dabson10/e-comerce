package org.github.dabson10.ecomerce.config;

import org.github.dabson10.ecomerce.usuarios.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    public SecurityFilterChain configuracion(HttpSecurity security, UsuarioRepository usuarioRepository) throws Exception{
        return security
                .csrf(c-> c.disable())
                .authorizeHttpRequests(http ->
                        http.requestMatchers("/users/create","/users/reed")
                                .permitAll().anyRequest().authenticated() )
                .sessionManagement( session ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .formLogin(form -> form.disable())
                .httpBasic(http -> http.disable())
                //Aqui pase solamente el repositorio del usuario
                .addFilterBefore(new JwtFilter(usuarioRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
