package com.ud.artesanias_bogota.services;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ud.artesanias_bogota.models.dtos.EmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio para gestionar el envío de correos electrónicos.
 * Proporciona funcionalidades como la generación de códigos de confirmación
 * y el envío de correos electrónicos personalizados con plantillas HTML.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * Constructor de la clase EmailService.
     * 
     * @param mailSender     componente para enviar correos electrónicos.
     * @param templateEngine motor de plantillas para generar contenido HTML dinámico.
     */
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Envía un correo electrónico con un código de verificación.
     *
     * @param email instancia de {@link EmailDTO} con los detalles del destinatario.
     * @return el código de confirmación generado.
     * @throws MessagingException si ocurre un error al crear o enviar el mensaje.
     */
    public int sendMail(EmailDTO email) throws MessagingException {
        try {
            // Crear mensaje MIME para el correo electrónico.
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Generar código de confirmación.
            int code = confirmationCode();
            String year = "©" + LocalDate.now().getYear() + " Artesanías Bogotá LTDA. All rights reserved.";

            // Configurar destinatario y asunto del correo.
            helper.setTo(email.getToEmail());
            helper.setSubject("Email verification");

            // Procesar la plantilla HTML para el correo.
            Context context = new Context();
            context.setVariable("code", code);
            context.setVariable("year", year);
            String htmlContent = templateEngine.process("email", context);

            // Configurar el contenido del mensaje.
            helper.setText(htmlContent, true);

            // Enviar el correo.
            mailSender.send(message);
            return code;
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electrónico", e);
        }
    }

    /**
     * Genera un código de confirmación aleatorio de 6 dígitos.
     *
     * @return el código de confirmación generado.
     */
    public int confirmationCode() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
}