package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Cliente.TipoCliente;
import com.mgcss.infrastructure.persistence.JpaClienteRepository;
import com.mgcss.service.ClienteService;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class TestClienteService {
	@Mock
    private JpaClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;
    @Test
    void consultarClienteCorrectamente() {
        Cliente c = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        Cliente resultado = clienteService.consultarCliente(1L);
        assertEquals(c, resultado);
        System.out.println("Cliente " + resultado.getNombre() + " consultado correctamente");
    }

    @Test
    void lanzarExcepcionSiClienteNoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.consultarCliente(1L);
        });
        System.out.println(e.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void bloquearClienteCorrectamente() {
        Cliente c = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        clienteService.bloquear(1L);
        verify(clienteRepository).save(c);
        System.out.println("Cliente " + c.getId() + " bloqueado correctamente");
    }

    @Test
    void ascenderClienteCorrectamente() {
        Cliente c = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        clienteService.ascender(1L);
        verify(clienteRepository).save(c);
        System.out.println("Cliente " + c.getId() + " ascendido correctamente");
    }
    
    @Test
    void deberiaObtenerMetricasClientes() {
        Cliente premium = new Cliente(1L, "Ana", "ana@test.com", Cliente.TipoCliente.PREMIUM);
        Cliente standard = new Cliente(2L, "Juan", "juan@test.com", Cliente.TipoCliente.STANDARD);

        when(clienteRepository.findAll()).thenReturn(List.of(premium, standard));

        Map<String, Object> metricas = clienteService.obtenerMetricas();

        assertEquals(2, metricas.get("totalClientes"));
        assertEquals(1L, metricas.get("premium"));
        assertEquals(1L, metricas.get("standard"));
    }
}
