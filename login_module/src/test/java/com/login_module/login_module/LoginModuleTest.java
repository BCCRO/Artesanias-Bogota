//package com.login_module.login_module;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.ud.login_module.Auth.AuthController;
//import com.ud.login_module.Auth.AuthResponse;
//import com.ud.login_module.Auth.AuthService;
//import com.ud.login_module.Auth.LoginRequest;
//
//@WebMvcTest(AuthController.class)
//public class LoginModuleTest {
////  @Autowired
////  private MockMvc mockMvc;
////
////  @MockBean
////  private AuthService authService;
////
////  @Test
////  void shouldReturnLoginToken() throws Exception {
////    // Preparar datos
////        LoginRequest request = new LoginRequest("juan.perez@example.com", "password123");
////
////        // Configurar Mock del servicio
////        //when(authService.login(request)).thenReturn();
////
////        // Ejecutar y verificar
////        mockMvc.perform(post("/auth/login")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content("{\"email\":\"juan.perez@example.com\", \"password\":\"password123\"}"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.token", is("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6QGV4YW1wbGUuY29tIiwiaWF0IjoxNzMzNTAzODE1LCJleHAiOjE3MzM1MDUyNTV9.nLx6wxHkEUWA7IIVMVbytB_0n94p-9DHoP-BSCI-h9M")));
////    }
//  }
