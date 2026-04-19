package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Cliente.TipoCliente;

public class SolicitudTest {
	@Test
	void noCerrarEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud solicitud = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> {
			solicitud.cerrar();
		});
	}



	@Test
	void cerrarSolicitudEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), c);
		s.cerrar();
		assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
	}

	@Test
	void cambiarSolicitudAbiertaAEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		s.iniciarProceso();
		assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
	}

	@Test
	void noCerrarSolicitudYaCerrada() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.CERRADA, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> s.cerrar());
	}

	@Test
	void noIniciarProcesoSiYaEstaEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
	}

	@Test
	void noIniciarProcesoSiEstaCerrada() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.CERRADA, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
	}

	@Test
	void noCrearSolicitudConIdNegativo() {

		assertThrows(IllegalArgumentException.class, () -> {
			Cliente c = new Cliente(-1L, "", "", TipoCliente.STANDARD);
			Solicitud s = new Solicitud(-1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		});
	}

	@Test
	void noCrearSolicitudSinEstado() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		assertThrows(IllegalArgumentException.class, () -> {
			Solicitud solicitud = new Solicitud(1L, "", null, LocalDateTime.now(), c);
		});
	}

	@Test
	void noCrearSolicitudSinFecha() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		assertThrows(IllegalArgumentException.class, () -> {
			Solicitud solicitud = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, null, c);
		});
	}

	@Test
	void noCrearSolicitudConFechaFutura() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		assertThrows(IllegalArgumentException.class, () -> {
			Solicitud solicitud = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now().plusDays(1), c);
		});
	}


	@Test
	void noAsignarTecnicoNull() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		assertThrows(IllegalArgumentException.class, () -> s.asignarTecnico(null));
	}
	@Test
	void testReabrirSolicitudExitosa() {
	    Cliente c = new Cliente(1L, "P", "p@p.com", TipoCliente.STANDARD);
	    Solicitud s = new Solicitud(1L, "D", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
	    
	    s.iniciarProceso();
	    s.asignarTecnico(new Tecnico("F", true, "IT"));
	    s.cerrar(); // Ahora está CERRADA
	    
	    s.reabrirSolicitud();
	    
	    assertEquals(EstadoSolicitud.ABIERTA, s.getEstado());
	    assertNull(s.getTecnico());
	}
}
