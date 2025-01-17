package com.ud.login_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.ud.login_module.jwt.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @SuppressWarnings("unused")
  private final AuthenticationProvider authProvider;
  @SuppressWarnings("unused")
  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http
    .csrf(csrf ->
      csrf
      .disable())
    .authorizeHttpRequests(authRequest-> 
      authRequest
        .requestMatchers("/auth/**").permitAll()
              .requestMatchers("/**") // Permite el acceso a todos los endpoints.
              .permitAll()
        )
    .build();
  }

}
