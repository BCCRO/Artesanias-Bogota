package com.ud.login_module.services;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ud.login_module.models.dtos.EmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio para el envío de correos electrónicos.
 * Incluye funcionalidades para generar códigos de verificación y procesar plantillas de correo.
 */
@Service // Marca esta clase como un servicio gestionado por Spring.
public class EmailService {

  // Instancia para enviar correos electrónicos.
  private final JavaMailSender mailSender;

  // Motor de plantillas para procesar contenido HTML.
  private final TemplateEngine templateEngine;

  /**
   * Constructor para inicializar las dependencias del servicio.
   *
   * @param mailSender Instancia de JavaMailSender para el envío de correos.
   * @param templateEngine Motor de plantillas Thymeleaf para procesar correos en HTML.
   */
  public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  /**
   * Envía un correo electrónico con un código de verificación.
   *
   * @param email Un objeto EmailDTO que contiene la dirección del destinatario.
   * @return El código de verificación generado y enviado.
   * @throws MessagingException Si ocurre un error al crear o enviar el mensaje.
   */
  public int sendMail(EmailDTO email) throws MessagingException {
    try {
      // Crea un mensaje MIME para el correo.
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      // Genera un código de verificación.
      int code = confirmationCode();

      // Genera el pie de página con el año actual.
      String year = "©" + LocalDate.now().getYear() + " Artesanías Bogotá LTDA. All rights reserved.";

      // Configura el destinatario y el asunto del correo.
      helper.setTo(email.getToEmail());
      helper.setSubject("Email verification");

      // Procesa el contenido HTML utilizando Thymeleaf.
      Context context = new Context();
      context.setVariable("code", code);
      context.setVariable("year", year);
      String htmlCont = templateEngine.process("email", context);

      // Configura el contenido del correo.
      helper.setText(htmlCont, true);

      // Envía el mensaje.
      mailSender.send(message);

      // Retorna el código de verificación generado.
      return code;
    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el correo electrónico", e);
    }
  }

  /**
   * Genera un código de verificación de 6 dígitos.
   *
   * @return Un código de verificación aleatorio.
   */
  public int confirmationCode() {
    // Genera un número aleatorio entre 100000 y 999999.
    return ThreadLocalRandom.current().nextInt(100000, 1000000);
  }
}
