package com.gremio.controller;

import com.gremio.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Deshabilita filtros de seguridad en MockMvc para pruebas
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private String loginJson;
    private String loginJsonFallido;

    @BeforeEach
    void setup() {
        loginJson = """
                {
                    "correoElectronico": "admin@gremio.com",
                    "contrasena": "admi123"
                }
                """;
        loginJsonFallido = """
                {
                    "correoElectronico": "admin@gremio.com",
                    "contrasena": "123456"
                }
                """;
    }

    @Test
    void login_exitoso() throws Exception {
        Mockito.when(authService.login("admin@gremio.com", "admi123"))
               .thenReturn("jwt-token-fake");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").value("jwt-token-fake"));
    }

    @Test
    void login_fallido() throws Exception {
        Mockito.when(authService.login("admin@gremio.com", "123456"))
               .thenThrow(new RuntimeException("Credenciales inválidas"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJsonFallido))
               .andExpect(status().isUnauthorized());
    }
}
