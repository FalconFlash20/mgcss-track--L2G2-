package com.mgcss.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mgcss.api.controller.ClienteController;
import com.mgcss.domain.Cliente;
import com.mgcss.service.ClienteService;

@WebMvcTest(ClienteController.class)
 class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteservice;

    @Test
    void deberiaCrearCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);

        when(clienteservice.crearCliente(org.mockito.ArgumentMatchers.any(Cliente.class))).thenReturn(cliente);

        String json = """
                {
                    "nombre":"Alejandro",
                    "email":"alex@test.com",
                    "tipoCLiente":"STANDARD"
                }
                """;

        mockMvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Alejandro")).andExpect(jsonPath("$.email").value("alex@test.com"))
                .andExpect(jsonPath("$.tipoCliente").value("STANDARD"));
    }

    @Test
    void deberiaConsultarCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        when(clienteservice.consultarCliente(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Alejandro"))
                .andExpect(jsonPath("$.tipoCliente").value("PREMIUM"));
    }

    @Test
    void deberiaListarClientes() throws Exception {
        Cliente c1 = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);

        Cliente c2 = new Cliente(2L, "Irene", "irene@test.com", Cliente.TipoCliente.PREMIUM);

        when(clienteservice.listar()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/clientes")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deberiaAscenderCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "Alejandro", "alex@universidad.edu", Cliente.TipoCliente.PREMIUM);

        doNothing().when(clienteservice).ascender(1L);

        when(clienteservice.consultarCliente(1L)).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/1/ascender")).andExpect(status().isOk());
    }

    @Test
    void deberiaBloquearCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);

        doNothing().when(clienteservice).bloquear(1L);

        when(clienteservice.consultarCliente(1L)).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/1/bloquear")).andExpect(status().isOk());
    }

    @Test
    void deberiaDesbloquearCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);

        doNothing().when(clienteservice).desbloquear(1L);

        when(clienteservice.consultarCliente(1L)).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/1/desbloquear")).andExpect(status().isOk());
    }
}