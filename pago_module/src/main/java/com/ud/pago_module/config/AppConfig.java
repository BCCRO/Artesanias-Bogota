package com.ud.pago_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración general para la aplicación.
 * Proporciona configuraciones específicas, como la gestión de codificación de contraseñas.
 */
@Configuration // Indica que esta clase contiene configuraciones de Spring.
public class AppConfig {

  /**
   * Define un bean para codificar contraseñas utilizando BCrypt.
   * 
   * @return Una instancia de PasswordEncoder que utiliza BCrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Implementación de codificación BCrypt para contraseñas.
  }
}
