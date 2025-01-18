package com.ud.login_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.ud.login_module.jwt.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration // Marca esta clase como una clase de configuración de Spring.
@EnableWebSecurity // Habilita la seguridad web en la aplicación.
@RequiredArgsConstructor // Genera un constructor para los campos finales (final) declarados en la clase.
public class SecurityConfig {

  // Proveedor de autenticación configurado previamente.
  @SuppressWarnings("unused")
  private final AuthenticationProvider authProvider;

  // Filtro para manejar la autenticación basada en JWT.
  @SuppressWarnings("unused")
  private final JwtAuthFilter jwtAuthFilter;

  /**
   * Configura la cadena de filtros de seguridad.
   * Define las políticas de acceso y los filtros aplicados a las solicitudes HTTP.
   *
   * @param http Objeto HttpSecurity para configurar la seguridad de las solicitudes.
   * @return Una instancia de SecurityFilterChain.
   * @throws Exception Si ocurre un error durante la configuración.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        // Deshabilita la protección CSRF, ya que no es necesaria para APIs REST.
        .csrf(csrf -> csrf.disable())
        // Configura las reglas de autorización para las solicitudes HTTP.
        .authorizeHttpRequests(authRequest -> 
            authRequest
                .requestMatchers("/auth/**").permitAll() // Permite el acceso a todas las rutas bajo "/auth/**".
                .requestMatchers("/**").permitAll() // Permite el acceso a todos los demás endpoints.
        )
        .build(); // Construye y retorna la cadena de filtros configurada.
  }
}
