package com.ud.pago_module.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


@Configuration
@EnableWebSecurity
public class SecurityConfig{


//  @Value("${jwt.secret}")
private static final String SECRET_KEY = "8e5a564ea09a55809097ac5fc4fc81e38a1c9cb3cea15a23b9f2c39789191ceab1ebb7993973dfef28417fe460317f772f4094b743820edaf81bfe357cbe4104";

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http
    .csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(authRequest->
            /**
             * TODO configurarlo mejor, un solo requestMatchers para los endpoints permitidos y luego validar por roles
             */
      authRequest
//        .requestMatchers("/api/productos/productos")
//              .permitAll()
//        .requestMatchers("/api/usuarios/create/cliente")
//              .permitAll()
//        .requestMatchers("/**")
////              .permitAll()
//              .authenticated()
          .requestMatchers("/**")
              .permitAll()

    )
    .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.decoder(jwtDecoder())) // Configura el decoder JWT
    )
    .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No se usa sesión
    )
//    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
    .build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    Key secretKey = Keys.hmacShaKeyFor(keyBytes);

    return NimbusJwtDecoder
            .withSecretKey((SecretKey) secretKey)
            .build();
  }

}
