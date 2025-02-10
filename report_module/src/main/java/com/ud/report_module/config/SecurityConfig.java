package com.ud.report_module.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.SecretKey;
import java.security.Key;

/**
 * Configuración de seguridad para la aplicación.
 * Incluye configuraciones para el manejo de JWT y políticas de seguridad HTTP.
 */
@Configuration // Marca esta clase como una configuración de Spring.
@EnableWebSecurity // Habilita la seguridad web para la aplicación.
public class SecurityConfig {

  // Clave secreta utilizada para decodificar JWT.
  private static final String SECRET_KEY = "8e5a564ea09a55809097ac5fc4fc81e38a1c9cb3cea15a23b9f2c39789191ceab1ebb7993973dfef28417fe460317f772f4094b743820edaf81bfe357cbe4104";

  /**
   * Configura la cadena de filtros de seguridad.
   * 
   * @param http El objeto HttpSecurity para configurar las reglas de seguridad HTTP.
   * @return Una instancia de SecurityFilterChain con las configuraciones aplicadas.
   * @throws Exception Si ocurre un error al configurar la seguridad.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http
        .cors(cors -> cors.disable())
        .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF.
        .authorizeHttpRequests(authRequest -> 
            authRequest
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite preflight requests
                .requestMatchers("/api/reportes/healthcheck").permitAll()
                .anyRequest().authenticated() // Restrige acceso a todos los endpoints.
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.decoder(jwtDecoder())) // Configura el decodificador JWT.
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura la aplicación como stateless.
        )
        .build(); // Devuelve la cadena de filtros configurada.
  }

  /**
   * Configura el decodificador JWT utilizando la clave secreta.
   * 
   * @return Una instancia de JwtDecoder para validar y decodificar tokens JWT.
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    // Decodifica la clave secreta desde Base64.
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    Key secretKey = Keys.hmacShaKeyFor(keyBytes);

    // Crea un decodificador JWT con la clave secreta.
    return NimbusJwtDecoder
        .withSecretKey((SecretKey) secretKey)
        .build();
  }
}