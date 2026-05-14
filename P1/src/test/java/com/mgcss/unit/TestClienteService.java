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

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Cliente.TipoCliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.service.ClienteService;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class TestClienteService {
	@Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService service;
    @Test
    void consultarClienteCorrectamente() {
        Cliente c = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        Cliente resultado = service.consultarCliente(1L);
        assertEquals(c, resultado);
        System.out.println("Cliente " + resultado.getNombre() + " consultado correctamente");
    }

    @Test
    void lanzarExcepcionSiClienteNoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            service.consultarCliente(1L);
        });
        System.out.println(e.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void bloquearClienteCorrectamente() {
        Cliente c = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        service.bloquear(1L);
        verify(clienteRepository).save(c);
        System.out.println("Cliente " + c.getId() + " bloqueado correctamente");
    }

    @Test
    void ascenderClienteCorrectamente() {
        Cliente c = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        service.ascender(1L);
        verify(clienteRepository).save(c);
        System.out.println("Cliente " + c.getId() + " ascendido correctamente");
    }
}
