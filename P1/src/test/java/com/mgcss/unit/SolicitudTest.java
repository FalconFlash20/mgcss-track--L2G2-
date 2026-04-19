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
	public void noCerrarEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud solicitud = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> {
			solicitud.cerrar();
		});
	}



	@Test
	public void cerrarSolicitudEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), c);
		s.cerrar();
		assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
	}

	@Test
	public void cambiarSolicitudAbiertaAEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		s.iniciarProceso();
		assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
	}

	@Test
	public void noCerrarSolicitudYaCerrada() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.CERRADA, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> s.cerrar());
	}

	@Test
	public void noIniciarProcesoSiYaEstaEnProceso() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
	}

	@Test
	public void noIniciarProcesoSiEstaCerrada() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.CERRADA, LocalDateTime.now(), c);
		assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
	}

	@Test
	public void noCrearSolicitudConIdNegativo() {

		assertThrows(IllegalArgumentException.class, () -> {
			Cliente c = new Cliente(-1L, "", "", TipoCliente.STANDARD);
			Solicitud s = new Solicitud(-1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		});
	}

	@Test
	public void noCrearSolicitudSinEstado() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		assertThrows(IllegalArgumentException.class, () -> {
			Solicitud solicitud = new Solicitud(1L, "", null, LocalDateTime.now(), c);
		});
	}

	@Test
	public void noCrearSolicitudSinFecha() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		assertThrows(IllegalArgumentException.class, () -> {
			Solicitud solicitud = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, null, c);
		});
	}

	@Test
	public void noCrearSolicitudConFechaFutura() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		assertThrows(IllegalArgumentException.class, () -> {
			Solicitud solicitud = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now().plusDays(1), c);
		});
	}


	@Test
	public void noAsignarTecnicoNull() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		assertThrows(IllegalArgumentException.class, () -> s.asignarTecnico(null));
	}
	@Test
	public void testReabrirSolicitudExitosa() {
	    Cliente c = new Cliente(1L, "P", "p@p.com", TipoCliente.STANDARD);
	    Solicitud s = new Solicitud(1L, "D", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
	    
	    s.iniciarProceso();
	    s.asignarTecnico(new Tecnico("F", true, "IT"));
	    s.cerrar(); // Ahora está CERRADA
	    
	    s.reabrirSolicitud();
	    
	    assertEquals(EstadoSolicitud.ABIERTA, s.getEstado());
	    assertNull(s.getTecnico());
	}
	@Test
	void MarcarUrgenteClientePremium() {
	    Cliente vip = new Cliente(1L, "Empresa", "info@empresa.com", Cliente.TipoCliente.PREMIUM);
	    Solicitud s = new Solicitud(1L, "Desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), vip);
	    
	    s.marcarComoUrgente();
	    assertTrue(s.isUrgente());
	}

	@Test
	void ErrorUrgenteClienteEstandarDescCorta() {
		Cliente normal = new Cliente(1L, "Pepe", "p@p.com", Cliente.TipoCliente.STANDARD);
	    // Caso 1: Estándar con descripción corta (Salta el último throw)
	    Solicitud s1 = new Solicitud(1L, "Corta", EstadoSolicitud.ABIERTA, LocalDateTime.now(), normal);
	    assertThrows(IllegalArgumentException.class, () -> s1.marcarComoUrgente());

	    // Caso 2: Estándar con descripción larga (Entra en el else if)
	    Solicitud s2 = new Solicitud(2L, "Descripción suficientemente larga para test", EstadoSolicitud.ABIERTA, LocalDateTime.now(), normal);
	    s2.marcarComoUrgente();
	    assertTrue(s2.isUrgente());
	}
	
}
