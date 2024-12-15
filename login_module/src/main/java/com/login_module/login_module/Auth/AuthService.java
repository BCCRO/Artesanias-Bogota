package com.login_module.login_module.Auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import com.login_module.login_module.User.UserRepository;
import com.login_module.login_module.User.Usuario;
import com.login_module.login_module.error.ResponseException;
import com.login_module.login_module.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepo;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;     

  public AuthResponse login(LoginRequest request) {
      if (userRepo.findByEmail(request.getEmail()).isEmpty()) {
        System.out.println("User not found");
        throw new ResponseException("User not found", 404);
      }
      authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      Usuario user = userRepo.findByEmail(request.getEmail()).orElseThrow();
      String token = jwtService.getToken(user);
      return AuthResponse.builder()
      .token(token)
      .primerNombre(user.getPrimerNombre())
      .primerApellido(user.getPrimerApellido())
      .segundoNombre(user.getSegundoNombre())
      .segundoApellido(user.getSegundoApellido())
      .documento(user.getDocumento())
      .email(user.getEmail())
      .fechaCreacion(user.getFechaCreacion())
      .fechaNacimiento(user.getFechaNacimiento())
      .direccion(user.getDireccion())
      .telefono(user.getTelefono())
      .isActive(user.isActivo())
      .roles(getRoleNames(user)).build();
    
    
  }
  private List<String> getRoleNames(UserDetails user){
    return user.getAuthorities().stream().map(GrantedAuthority:: getAuthority).toList();
  } 
}

