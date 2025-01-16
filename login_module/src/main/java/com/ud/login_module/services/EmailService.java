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

@Service
public class EmailService{
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  public EmailService(JavaMailSender mailSender,TemplateEngine templateEngine){
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  public int sendMail(EmailDTO email) throws MessagingException{
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
      int code = confirmationCode();
      String year = "©"+LocalDate.now().getYear()+ " Artesanias Bogotá LTDA. All rights reserved.";

      helper.setTo(email.getToEmail());
      helper.setSubject("Email verification");
      
      //Procesar el template
      Context context = new Context();
      context.setVariable("code", code);
      context.setVariable("year", year);
      String htmlCont = templateEngine.process("email",context);

      helper.setText(htmlCont,true);
      mailSender.send(message);
      return code;
    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el correo electronico",e);
    }
  
  }

  public int confirmationCode(){
    int code = ThreadLocalRandom.current().nextInt(100000,1000000);
    return code;
  }
}

