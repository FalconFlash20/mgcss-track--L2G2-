package com.mgcss.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mgcss.api.controller.DashboardWebController;
import com.mgcss.service.ClienteService;
import com.mgcss.service.SolicitudService;
import com.mgcss.service.TecnicoService;

@WebMvcTest(DashboardWebController.class)
class DashboardWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudService solicitudService;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private TecnicoService tecnicoService;

    @Test
    void deberiaMostrarDashboard() throws Exception {

        when(solicitudService.obtenerMetricas()).thenReturn(Map.of("totalSolicitudes", 10));
        when(clienteService.obtenerMetricas()).thenReturn(Map.of("totalClientes", 5));
        when(tecnicoService.obtenerMetricas()).thenReturn(Map.of("totalTecnicos", 3));
        mockMvc.perform(get("/dashboard")).andExpect(status().isOk()).andExpect(view().name("dashboard"));
    }
}