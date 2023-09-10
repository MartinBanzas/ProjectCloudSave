package com.luv2code.springboot.thymeleafdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

@Configuration
public class SecurityConfig {


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/juegos/files/upload").hasRole("ADMIN")
                                .requestMatchers("/juegos/files/delete/{id}").hasRole("ADMIN")
                                .requestMatchers("/juegos/lista").permitAll()
                                .requestMatchers("/juegos/formAdd").hasRole("ADMIN")
                                .requestMatchers("/juegos/delete/{id}").hasRole("ADMIN")
                                .requestMatchers("/juegos/img/uploadImg").hasRole("ADMIN")
                                .requestMatchers("/juegos/update/{id}").hasRole("ADMIN")

                                .anyRequest().permitAll()
                                )
                .formLogin(form ->
                        form
                                .loginPage("/loginUser")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .rememberMe(rememberMe ->
                        rememberMe
                                .key("ashkdfjas212766-vzbxghgh-782163") // Cambia esto por una clave única
                                .rememberMeCookieName("cookieWebMartin")
                                .tokenValiditySeconds(6048000) // Duración del token en segundos (10 semanas en este ejemplo)
                )

                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(1) // Número máximo de sesiones simultáneas permitidas
                                .maxSessionsPreventsLogin(false) // Si establecer esto en true debe evitar que un usuario inicie sesión en otra ubicación si ya tiene una sesión activa.
                                .expiredUrl("/loginUser?expired") // URL a la que redirigir si la sesión expira
                )

                .logout(logout -> logout
                        .logoutUrl("/logout") // URL de cierre de sesión personalizada
                        .deleteCookies("cookieWebMartin") // Eliminar la cookie "Recuérdame" al cerrar sesión
                        .invalidateHttpSession(true) // Invalidar la sesión HTTP
                        .logoutSuccessUrl("/juegos/lista") // Redirigir a la página de inicio de sesión con un mensaje de cierre de sesión
                );
        return http.build();
    }

}



