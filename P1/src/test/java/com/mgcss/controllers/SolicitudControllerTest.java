package com.mgcss.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
<<<<<<< HEAD

=======
>>>>>>> c830738 (fix: rename api package directories)
import com.mgcss.api.controller.SolicitudController;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.service.SolicitudService;

@WebMvcTest(SolicitudController.class)
 class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudService solicitudservice;

    @Test
    void deberiaCrearSolicitud() throws Exception {

        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud( "Error conexión", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        when(solicitudservice.crearSolicitud("Error conexión", 1L)).thenReturn(solicitud);

        String json = """
                {
                    "descripcion":"Error conexión",
                    "clienteId":1
                }
                """;

        mockMvc.perform(post("/api/solicitudes").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andExpect(jsonPath("$.descripcion").value("Error conexión"))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void deberiaConsultarSolicitud() throws Exception {
        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud("Fallo servidor", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        when(solicitudservice.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(get("/api/solicitudes/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$.descripcion").value("Fallo servidor"))
        .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void deberiaListarSolicitudes() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);
    	Solicitud s1 = new Solicitud("Error 1", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);
    	Solicitud s2 = new Solicitud("Error 2", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente);

        when(solicitudservice.listar()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/solicitudes")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deberiaReabrirSolicitud() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);
        Solicitud solicitud = new Solicitud("Incidencia", EstadoSolicitud.CERRADA, LocalDateTime.now(), cliente);
        doNothing().when(solicitudservice).reabrirSolicitud(1L);
        when(solicitudservice.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(patch("/api/solicitudes/1/reabrir")).andExpect(status().isOk());
    }

    @Test
    void deberiaAsignarTecnico() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);

        Solicitud solicitud = new Solicitud("Hardware roto", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        doNothing().when(solicitudservice).asignarTecnico(1L, 5L);
        when(solicitudservice.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(put("/api/solicitudes/1/asignarTecnico").param("tecnicoId", "5")).andExpect(status().isOk());
    }

    @Test
    void deberiaCambiarEstado() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud("Problema red", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);
        
        doNothing().when(solicitudservice).cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);

        when(solicitudservice.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(put("/api/solicitudes/1/cambiarEstado").param("estado", "EN_PROCESO")).andExpect(status().isOk());
    }
}