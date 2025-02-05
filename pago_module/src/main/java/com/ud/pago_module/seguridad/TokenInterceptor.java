package com.ud.pago_module.seguridad;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class TokenInterceptor implements HandlerInterceptor{

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer ")){
      token = token.substring(7);
      request.setAttribute("authtoken", token);
    }else{
      return false;
    }
    return true;
  }
}
