package com.ud.login_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ud.login_module.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration // Marca esta clase como una clase de configuración de Spring.
@RequiredArgsConstructor // Genera un constructor para los campos finales (final) declarados en la clase.
public class AppConfig {

  // Repositorio para interactuar con los datos del usuario.
  private final UserRepository userRepository;

  /**
   * Configura el bean de AuthenticationManager.
   *
   * @param config Configuración de autenticación de Spring.
   * @return Una instancia de AuthenticationManager.
   * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
   */
  @Bean
  public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * Configura el proveedor de autenticación.
   * Utiliza el DaoAuthenticationProvider para validar credenciales con una fuente de datos específica.
   *
   * @return Una instancia de AuthenticationProvider.
   */
  @Bean
  public AuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailService()); // Configura el servicio de detalles del usuario.
    authProvider.setPasswordEncoder(passwordEncoder()); // Configura el codificador de contraseñas.
    return authProvider;
  }

  /**
   * Configura el codificador de contraseñas.
   * Utiliza BCrypt, un algoritmo seguro para almacenar contraseñas.
   *
   * @return Una instancia de PasswordEncoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configura el servicio para cargar detalles del usuario.
   * Busca un usuario por su correo electrónico y lanza una excepción si no se encuentra.
   *
   * @return Una instancia de UserDetailsService.
   */
  @Bean
  public UserDetailsService userDetailService() {
    return email -> userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}