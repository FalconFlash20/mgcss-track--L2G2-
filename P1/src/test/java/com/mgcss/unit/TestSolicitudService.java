package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
import com.mgcss.domain.Tecnico;
import com.mgcss.infrastructure.persistence.JpaClienteRepository;
import com.mgcss.infrastructure.persistence.JpaSolicitudRepository;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;
import com.mgcss.domain.Cliente.TipoCliente;
import com.mgcss.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
 class TestSolicitudService {
	@Mock
	private JpaSolicitudRepository solicitudRepository;
	@Mock
	private JpaTecnicoRepository tecnicoRepository;
	@Mock
	private JpaClienteRepository clienteRepository;
	@InjectMocks
	private SolicitudService solicitudService;
	
	@Test
	void deberiaLanzarExcepcionSiTecnicoInactivo() {
		Tecnico t = new Tecnico("Juan", false, Tecnico.Especialidad.SOFTWARE);
		Cliente c=new Cliente(1L,"Pepe","pepe@test.com",TipoCliente.STANDARD);
		Solicitud s = new Solicitud("desc",EstadoSolicitud.ABIERTA, LocalDateTime.now(),c);
		
		when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
	    when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(t));
	    
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
		    solicitudService.asignarTecnico(1L, 2L);
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

	    solicitudService.asignarTecnico(1L, 2L);

	    assertEquals(t, s.getTecnico());
	    verify(solicitudRepository).save(s);
	    
	    System.out.println("Tecnico activo con solicitud " + s.getId() + " asignada");
	}

	@Test
	void lanzarExcepcionSiSolicitudNoExiste() {
	    when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());
	    Exception e = assertThrows(IllegalArgumentException.class,
	        () -> { solicitudService.asignarTecnico(1L, 2L);});
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
	            () -> { solicitudService.asignarTecnico(1L, 2L);});
	    System.out.println(e.getMessage());
	    verify(solicitudRepository, never()).save(any());
	}
	@Test
	void lanzarExcepcionSiReabrirSolicitudNo() {
	    Cliente cliente = new Cliente(1L, "Fran", "fran@pccom.com", Cliente.TipoCliente.STANDARD);
	    Solicitud solicitudAbierta = new Solicitud("Reparación gráfica",EstadoSolicitud.ABIERTA,LocalDateTime.now(), cliente);
	    when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitudAbierta));
	    Long idSol=1L;
	    Exception e=assertThrows(RuntimeException.class, () -> {
	        solicitudService.reabrirSolicitud(idSol);
	    });
		System.out.println(e.getMessage());
		verify(solicitudRepository, never()).save(any());
	}
	@Test
	void lanzarExcepcionSiAsignasTecnicoASolCerrada() {
		Tecnico t = new Tecnico("Juan", true, Tecnico.Especialidad.SOFTWARE);
		Cliente c = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
		Solicitud s = new Solicitud("desc", EstadoSolicitud.CERRADA, LocalDateTime.now(), c);	
		when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
		when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(t));
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			solicitudService.asignarTecnico(1L, 2L);
		});	
		System.out.println(e.getMessage());
		verify(solicitudRepository, never()).save(any());
	}
	
	@Test
	void deberiaObtenerMetricas() {
	    Cliente cliente = new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
	    Solicitud abierta = new Solicitud("A", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente);
	    Solicitud proceso = new Solicitud("B", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente);
	    Solicitud cerrada = new Solicitud("C", EstadoSolicitud.CERRADA, LocalDateTime.now(), cliente);

	    when(solicitudRepository.findAll()).thenReturn(List.of(abierta, proceso, cerrada));

	    Map<String,Object> metricas =solicitudService.obtenerMetricas();

	    assertEquals(3, metricas.get("totalSolicitudes"));
	    assertEquals(1L, metricas.get("abiertas"));
	    assertEquals(1L, metricas.get("enProceso"));
	    assertEquals(1L, metricas.get("cerradas"));
	}
}
