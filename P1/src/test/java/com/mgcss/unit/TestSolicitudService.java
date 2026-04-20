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
import com.mgcss.domain.TecnicoRepository;
import com.mgcss.domain.Cliente.TipoCliente;
import com.mgcss.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class TestSolicitudService {
	@Mock
	private SolicitudRepository solicitudRepository;
	@Mock
	private TecnicoRepository tecnicoRepository;
	@InjectMocks
	private SolicitudService service;
	
	@Test
	void deberiaLanzarExcepcionSiTecnicoInactivo() {
		Tecnico t = new Tecnico("Juan", false, Tecnico.Especialidad.SOFTWARE);
		Cliente c=new Cliente(1L,"Pepe","pepe@test.com",TipoCliente.STANDARD);
		Solicitud s = new Solicitud("desc",EstadoSolicitud.ABIERTA, LocalDateTime.now(),c);
		
		when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
	    when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(t));
	    
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
		    service.asignarTecnico(1L, 2L);
		});
		System.out.println(e.getMessage());
		verify(solicitudRepository, never()).save(any());
	}
	
	@Test
	void asignarTecnicoCorrectamente() {
		Tecnico t = new Tecnico("Juan", true,Tecnico.Especialidad.SOFTWARE);
		Cliente c=new Cliente(1L,"Pepe","pepe@test.com",TipoCliente.STANDARD);
		Solicitud s = new Solicitud("desc",EstadoSolicitud.ABIERTA, LocalDateTime.now(),c);

	    when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
	    when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(t));

	    service.asignarTecnico(1L, 2L);

	    assertEquals(t, s.getTecnico());
	    verify(solicitudRepository).save(s);
	    
	    System.out.println("Tecnico activo con solicitud " + s.getId() + " asignada");
	}

	@Test
	void lanzarExcepcionSiSolicitudNoExiste() {
	    when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());
	    Exception e = assertThrows(IllegalArgumentException.class,
	        () -> { service.asignarTecnico(1L, 2L);});
	    System.out.println(e.getMessage());
	    
	    verify(solicitudRepository, never()).save(any());
	}
	
	@Test
	void lanzarExcepcionSiTecnicoNoExiste() {
	    Cliente c = new Cliente(1L,"Pepe","pepe@test.com",TipoCliente.STANDARD);
	    Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);

	    when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
	    when(tecnicoRepository.findById(2L)).thenReturn(Optional.empty());

	    Exception e = assertThrows(IllegalArgumentException.class,
	            () -> { service.asignarTecnico(1L, 2L);});
	    System.out.println(e.getMessage());
	    
	    verify(solicitudRepository, never()).save(any());
	}
}
