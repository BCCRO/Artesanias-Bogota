package com.ud.artesanias_bogota.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración general de la aplicación.
 * Esta clase define beans necesarios para el funcionamiento de la aplicación.
 */
@Configuration // Indica que esta clase contiene definiciones de beans para el contexto de Spring.
public class AppConfig {

  /**
   * Define el bean para el codificador de contraseñas.
   * Se utiliza BCryptPasswordEncoder para encriptar contraseñas de manera segura.
   * 
   * @return una instancia de PasswordEncoder basada en BCrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Devuelve una implementación de PasswordEncoder que utiliza el algoritmo BCrypt.
  }
}
