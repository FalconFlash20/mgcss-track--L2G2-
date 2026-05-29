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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mgcss.api.controller.SolicitudController;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.SolicitudService;

@WebMvcTest(SolicitudController.class)
 class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudService solicitudService;

    @Test
    void deberiaCrearSolicitud() throws Exception {

        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud( "Error conexión", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        when(solicitudService.crearSolicitud("Error conexión", 1L)).thenReturn(solicitud);

        String json = """
                {
                    "descripcion":"Error conexión",
                    "clienteId":1
                }
                """;

        mockMvc.perform(post("/api/solicitudes").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isCreated()).andExpect(jsonPath("$.descripcion").value("Error conexión"))
        .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void deberiaConsultarSolicitud() throws Exception {
        Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud("Fallo servidor", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(get("/api/solicitudes/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$.descripcion").value("Fallo servidor"))
        .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void deberiaListarSolicitudes() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);
    	Solicitud s1 = new Solicitud("Error 1", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);
    	Solicitud s2 = new Solicitud("Error 2", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente);

        when(solicitudService.listar()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/api/solicitudes")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deberiaReabrirSolicitud() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);
        Solicitud solicitud = new Solicitud("Incidencia", EstadoSolicitud.CERRADA, LocalDateTime.now(), cliente);
        doNothing().when(solicitudService).reabrirSolicitud(1L);
        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(patch("/api/solicitudes/1/reabrir")).andExpect(status().isOk());
    }

    @Test
    void deberiaAsignarTecnico() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.STANDARD);

        Solicitud solicitud = new Solicitud("Hardware roto", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        doNothing().when(solicitudService).asignarTecnico(1L, 5L);
        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(put("/api/solicitudes/1/asignarTecnico").param("tecnicoId", "5")).andExpect(status().isOk());
    }

    @Test
    void deberiaMapearTecnicoAsignado() throws Exception {
        Cliente cliente = new Cliente(1L, "Alex", "alex@test.com", Cliente.TipoCliente.STANDARD);
        Tecnico tecnico = new Tecnico("Fran", true, Tecnico.Especialidad.SOFTWARE);
        Solicitud solicitud = new Solicitud("Problema grave", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        solicitud.asignarTecnico(tecnico);

        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(get("/api/solicitudes/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$.tecnicoNombre").value("Fran"));
    }
    
    @Test
    void deberiaCambiarEstadoAEnProceso() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud("Problema red", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);
        
        doNothing().when(solicitudService).cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);

        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(put("/api/solicitudes/1/cambiarEstado").param("estado", "EN_PROCESO")).andExpect(status().isOk());
    }
    
    @Test
    void deberiaCambiarEstadoACerrada() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud("Problema red", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente);
        
        doNothing().when(solicitudService).cambiarEstado(1L, EstadoSolicitud.CERRADA);

        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(put("/api/solicitudes/1/cambiarEstado").param("estado", "CERRADA"))
        .andExpect(status().isOk());
    }
    
    @Test
    void deberiaCambiarEstadoAAbierta() throws Exception {
    	Cliente cliente = new Cliente(1L, "Alejandro", "alex@test.com", Cliente.TipoCliente.PREMIUM);

        Solicitud solicitud = new Solicitud("Problema red", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);

        doNothing().when(solicitudService).cambiarEstado(1L, EstadoSolicitud.ABIERTA);

        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitud);

        mockMvc.perform(put("/api/solicitudes/1/cambiarEstado").param("estado", "ABIERTA"))
        .andExpect(status().isOk());
    }
    
    @Test
    void deberiaDevolver400SiDescripcionEsVacia() throws Exception {
    	String json = """
            {
                "descripcion":"",
                "clienteId":1
            }
            """;

        mockMvc.perform(post("/api/solicitudes").contentType(MediaType.APPLICATION_JSON)
        .content(json)).andExpect(status().isBadRequest());
    }
    
    @Test
    void deberiaDevolver400SiEstadoEsInvalido() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/cambiarEstado").param("estado", "ESTADO_FAKE"))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void deberiaDevolver400SiClienteIdEsNull() throws Exception {
        String json = """
            {
                "descripcion":"Problema red",
                "clienteId":null
            }
            """;

        mockMvc.perform(post("/api/solicitudes").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void deberiaDevolver400SiFaltaTecnicoId() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/asignarTecnico"))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void deberiaObtenerMetricas() throws Exception {

        Map<String, Object> metricas = new HashMap<>();

        metricas.put("totalSolicitudes", 10);
        metricas.put("abiertas", 3);
        metricas.put("enProceso", 4);
        metricas.put("cerradas", 3);

        when(solicitudService.obtenerMetricas()).thenReturn(metricas);

        mockMvc.perform(get("/api/solicitudes/metricas")).andExpect(status().isOk())
        .andExpect(jsonPath("$.totalSolicitudes").value(10)).andExpect(jsonPath("$.abiertas").value(3));
    }
}