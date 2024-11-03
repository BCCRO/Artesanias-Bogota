package com.login_module.login_module.Protected;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProtectedController {

  @PostMapping("/something")
  public String Welcome() {
      //TODO: process POST request
      
      return "Welcome from my protected endpoint";
  }
  
  
}
