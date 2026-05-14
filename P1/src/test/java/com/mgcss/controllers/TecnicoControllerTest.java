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

import com.mgcss.api.Controller.TecnicoController;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;

@WebMvcTest(TecnicoController.class)
 class TecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TecnicoService tecnicoService;

    @Test
    void deberiaCrearTecnico() throws Exception {
        Tecnico tecnico = new Tecnico("Carlos", true, Tecnico.Especialidad.SOFTWARE);

        when(tecnicoService.crearTecnico(org.mockito.ArgumentMatchers.any(Tecnico.class))).thenReturn(tecnico);

        String json = """
                {
                    "nombre":"Carlos",
                    "especialidad":"SOFTWARE"
                }
                """;

        mockMvc.perform(post("/api/tecnicos").contentType(MediaType.APPLICATION_JSON).content(json))
        	.andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Carlos"))
        	.andExpect(jsonPath("$.activo").value(true)).andExpect(jsonPath("$.especialidad").value("SOFTWARE"));
    }

    @Test
    void deberiaConsultarTecnico() throws Exception {
        Tecnico tecnico = new Tecnico("Ana", true, Tecnico.Especialidad.REDES);

        when(tecnicoService.consultarTecnico(1L)).thenReturn(tecnico);

        mockMvc.perform(get("/api/tecnicos/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Ana"))
        	.andExpect(jsonPath("$.especialidad").value("REDES"));
    }

    @Test
    void deberiaListarTecnicos() throws Exception {
        Tecnico t1 = new Tecnico("Carlos", true, Tecnico.Especialidad.HARDWARE);
        Tecnico t2 = new Tecnico("Lucia", true, Tecnico.Especialidad.SOFTWARE);

        when(tecnicoService.listarTecnicos()).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/api/tecnicos")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deberiaActivarTecnico() throws Exception {
        Tecnico tecnico = new Tecnico("Mario", true, Tecnico.Especialidad.SOPORTE_GENERAL);

        doNothing().when(tecnicoService).activar(1L);

        when(tecnicoService.consultarTecnico(1L)).thenReturn(tecnico);

        mockMvc.perform(put("/api/tecnicos/1/activar")).andExpect(status().isOk());
    }

    @Test
    void deberiaDesactivarTecnico() throws Exception {
        Tecnico tecnico = new Tecnico("Laura", false, Tecnico.Especialidad.SEGURIDAD);

        doNothing().when(tecnicoService).desactivar(1L);

        when(tecnicoService.consultarTecnico(1L)).thenReturn(tecnico);

        mockMvc.perform(put("/api/tecnicos/1/desactivar")).andExpect(status().isOk());
    }

    @Test
    void deberiaActualizarEspecialidad() throws Exception {
        Tecnico tecnico = new Tecnico("Pedro", true, Tecnico.Especialidad.SOFTWARE);

        doNothing().when(tecnicoService).actualizarEspecialidad(1L, Tecnico.Especialidad.SOFTWARE);

        when(tecnicoService.consultarTecnico(1L)).thenReturn(tecnico);

        mockMvc.perform(put("/api/tecnicos/1/especialidad").param("nuevaesp", "SOFTWARE")).andExpect(status().isOk());
    }
}