//package com.ud.inventario_module.config;
//
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.io.DefaultResourceLoader;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
///**
// * Configuración para el manejo de envío de correos electrónicos en la aplicación.
// * Utiliza Spring Framework para configurar el servicio de JavaMailSender.
// */
//@Configuration
//@PropertySource("classpath:email.properties") // Indica el archivo de propiedades que contiene las credenciales de correo electrónico.
//public class EmailConfig {
//
//  @Value("${email.username}") // Inyecta el valor de la propiedad "email.username" del archivo email.properties.
//  private String email;
//
//  @Value("${email.password}") // Inyecta el valor de la propiedad "email.password" del archivo email.properties.
//  private String password;
//
//  /**
//   * Configura las propiedades específicas para el servidor de correo.
//   *
//   * @return un objeto Properties con la configuración de correo.
//   */
//  private Properties getMailProperties() {
//    Properties properties = new Properties();
//    properties.put("mail.smtp.auth", "true"); // Habilita la autenticación para el servidor SMTP.
//    properties.put("mail.smtp.starttls.enable", "true"); // Habilita STARTTLS para encriptación.
//    properties.put("mail.smtp.host", "smtp.gmail.com"); // Configura el servidor SMTP de Gmail.
//    properties.put("mail.smtp.port", "587"); // Establece el puerto SMTP para conexiones seguras.
//    return properties;
//  }
//
//  /**
//   * Define el bean para JavaMailSender, que se utiliza para enviar correos electrónicos.
//   *
//   * @return una instancia configurada de JavaMailSender.
//   */
//  @Bean
//  public JavaMailSender javaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setJavaMailProperties(getMailProperties()); // Asigna las propiedades del servidor de correo.
//    mailSender.setUsername(email); // Establece el nombre de usuario para la autenticación.
//    mailSender.setPassword(password); // Establece la contraseña para la autenticación.
//    return mailSender;
//  }
//
//  /**
//   * Define el bean para ResourceLoader, que permite cargar recursos de la aplicación.
//   *
//   * @return una instancia configurada de ResourceLoader.
//   */
//  @Bean
//  public ResourceLoader resourceLoader() {
//    return new DefaultResourceLoader();
//  }
//}