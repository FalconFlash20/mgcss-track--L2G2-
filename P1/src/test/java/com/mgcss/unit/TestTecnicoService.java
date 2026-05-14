package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import com.mgcss.service.TecnicoService;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class TestTecnicoService {

	@Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private TecnicoService service;

    @Test
    void consultarTecnicoCorrectamente() {
        Tecnico t = new Tecnico("Juan", true, Tecnico.Especialidad.SOFTWARE);
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(t));
        Tecnico resultado = service.consultarTecnico(1L);
        assertEquals(t, resultado);
        System.out.println("Tecnico " + resultado.getNombre() + " consultado correctamente");
    }

    @Test
    void lanzarExcepcionSiTecnicoNoExiste() {
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.empty());
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            service.consultarTecnico(1L);
        });
        System.out.println(e.getMessage());
        verify(tecnicoRepository, never()).save(any());
    }

    @Test
    void activarTecnicoCorrectamente() {
        Tecnico t = new Tecnico("Juan", false, Tecnico.Especialidad.SOFTWARE);
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(t));
        service.activar(1L);
        verify(tecnicoRepository).save(t);
        System.out.println("Tecnico " + t.getNombre() + " activado correctamente");
    }

    @Test
    void actualizarEspecialidadCorrectamente() {
        Tecnico t = new Tecnico("Juan", true, Tecnico.Especialidad.SOFTWARE);
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(t));
        service.actualizarEspecialidad(1L, Tecnico.Especialidad.HARDWARE);
        verify(tecnicoRepository).save(t);
        System.out.println("Especialidad de " + t.getNombre() + " actualizada");
    }
}