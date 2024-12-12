package com.ud.artesanias_bogota.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.artesanias_bogota.models.dtos.EmailDTO;
import com.ud.artesanias_bogota.services.EmailService;

import jakarta.mail.MessagingException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class EmailController {

  @Autowired
  EmailService emailService;

  @PostMapping("/email_auth")
      
      private ResponseEntity<?> sendEmail(@RequestBody EmailDTO email) throws MessagingException{
        Map<String,Object> response = new HashMap<>();
        System.out.println("Se envio el correo");
        try {
          int code= emailService.sendMail(email);
          response.put("confirmCode",code);
          //response.put("sendTime", 12);
          response.put("message","Ok");
          return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
          response.put("message","Oops, something went wrong!");
          return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
      }

  
  

}
