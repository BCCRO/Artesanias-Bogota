package com.ud.login_module.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import java.security.Key;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Configuration // Marca esta clase como una clase de configuración de Spring.
@EnableWebSecurity // Habilita la seguridad web en la aplicación.
@RequiredArgsConstructor // Genera un constructor para los campos finales (final) declarados en la clase.

public class SecurityConfig {

  // Clave secreta utilizada para la firma de los tokens JWT.
  private static final String SECRET_KEY = "8e5a564ea09a55809097ac5fc4fc81e38a1c9cb3cea15a23b9f2c39789191ceab1ebb7993973dfef28417fe460317f772f4094b743820edaf81bfe357cbe4104";

  /**
   * Configura la cadena de filtros de seguridad para las solicitudes HTTP.
   * Desactiva CSRF, define políticas de autorización y configura el soporte para JWT.
   *
   * @param http objeto HttpSecurity para configurar la seguridad.
   * @return la configuración construida de SecurityFilterChain.
   * @throws Exception si ocurre un error en la configuración.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .cors(cors -> cors.disable())
      .csrf(csrf -> csrf.disable()) // Desactiva la protección contra CSRF.
      .authorizeHttpRequests(authRequest ->
        authRequest
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite preflight requests
                .requestMatchers("/auth/login", "/api/email_auth", "/api/usuarios/create", "/api/usuarios/create/cliente","/api/usuarios/healthcheck")
                    .permitAll()
                .anyRequest() // Restringe el acceso a todos los endpoints.
                    .authenticated()
      )
      .oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt.decoder(jwtDecoder())) // Configura el decoder para tokens JWT.
      )
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Establece sesiones sin estado.
      )
      .build();
  }

  /**
   * Configura el decodificador de tokens JWT.
   * Decodifica y verifica la firma de los tokens utilizando la clave secreta.
   *
   * @return una instancia configurada de JwtDecoder.
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decodifica la clave secreta en formato Base64.
    Key secretKey = Keys.hmacShaKeyFor(keyBytes); // Crea una clave HMAC a partir de los bytes decodificados.

    return NimbusJwtDecoder
      .withSecretKey((SecretKey) secretKey) // Configura el decodificador con la clave secreta.
      .build();
  }
}