package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Cliente.TipoCliente;
import com.mgcss.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class TestSolicitudService {
	@Mock
	private SolicitudRepository repository;
	@InjectMocks
	private SolicitudService service;
	
	@Test
	void deberiaLanzarExcepcionSiTecnicoInactivo() {
		Tecnico t = new Tecnico("Juan", false,"");
		Cliente c=new Cliente(1L,"","",TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L,"",EstadoSolicitud.ABIERTA, LocalDateTime.now(),c);
		when(repository.findById(1L)).thenReturn(Optional.of(s));
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
		    service.asignarTecnico(1L, t);
		});
		System.out.println(e.getMessage());
		verify(repository, never()).save(any());
	}
	
	
	// Cuando se asigna un técnico válido, el servicio debe guardar la solicitud
	@Test
	void asignarTecnicoCorrectamente() {
		Tecnico t = new Tecnico("Juan", true,"");
		Cliente c=new Cliente(1L,"","",TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L,"",EstadoSolicitud.ABIERTA, LocalDateTime.now(),c);

	    when(repository.findById(1L)).thenReturn(Optional.of(s));

	    service.asignarTecnico(1L, t);

	    assertEquals(t, s.getTecnico());
	    verify(repository).save(s);
	    
	    System.out.println("Tecnico activo con solicitud " + s.getId() + " asignada");
	}
	
	//Si la solicitud no existe, lanzar excepción
	@Test
	void lanzarExcepcionSiSolicitudNoExiste() {
	    when(repository.findById(1L)).thenReturn(Optional.empty());
		Tecnico t = new Tecnico("Juan", true,"");
	    Exception e = assertThrows(IllegalArgumentException.class,
	        () -> { service.asignarTecnico(1L, t);});
	    System.out.println(e.getMessage());
	    
	    verify(repository, never()).save(any());
	}
}
