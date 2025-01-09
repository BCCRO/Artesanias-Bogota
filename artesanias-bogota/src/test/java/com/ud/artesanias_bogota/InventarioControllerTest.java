package com.ud.artesanias_bogota;

import com.ud.artesanias_bogota.controllers.InventarioController;
import com.ud.artesanias_bogota.services.ProductoHasPuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProductoHasPuntoVentaService productoHasPuntoVentaService;  //Importante instanciar el servicio, ya que no inciamos el servicio web
//
//    /**
//     * TODO aun es de prueba este codigo
//     */
//    @Test
//    void greetingShouldReturnMessageFromService() throws Exception {
//        // when(productoHasPuntoVentaService.greet()).thenReturn("Hello, Mock");
//        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, Mock")));
//    }

}
