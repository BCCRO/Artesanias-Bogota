package com.login_module.login_module.Auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import com.login_module.login_module.User.UserRepository;

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
        throw new ResponseException("User not found", 404);
      }
      if (!authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())).isAuthenticated()) {
        throw new ResponseException("Bad credentials", 403);
      }
      authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      UserDetails user = userRepo.findByEmail(request.getEmail()).orElseThrow();
      String token = jwtService.getToken(user);
      return AuthResponse.builder()
      .token(token)
      .email(user.getUsername())
      .roles(getRoleNames(user)).build();
    
    
  }
  private List<String> getRoleNames(UserDetails user){
    return user.getAuthorities().stream().map(GrantedAuthority:: getAuthority).toList();
  } 
}

