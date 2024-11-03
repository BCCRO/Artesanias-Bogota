package com.login_module.login_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.login_module.login_module.jwt.JwtAuthFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationProvider authProvider;
  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http
    .csrf(csrf ->
      csrf
      .disable())
    .authorizeHttpRequests(authRequest-> 
      authRequest
        .requestMatchers("auth/**").permitAll()
        .anyRequest().authenticated()
        )
    .formLogin(withDefaults())
    .sessionManagement(sessionManager->
    sessionManager
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authenticationProvider(authProvider)
    .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
    .build();
  }

}
