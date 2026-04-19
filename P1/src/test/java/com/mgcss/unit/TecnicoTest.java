package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Cliente.TipoCliente;
import com.mgcss.domain.Solicitud.EstadoSolicitud;

public class TecnicoTest {
	@Test
	void asignaTecnicoActivo() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(1L, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		Tecnico t = new Tecnico("Francisco", true, "");

		s.asignarTecnico(t);
		assertEquals(t, s.getTecnico());
	}
	@Test
	void asignaTecnicoInactivo() {
		Cliente c = new Cliente(1L, "", "", TipoCliente.STANDARD);
		Solicitud s = new Solicitud(null, "", EstadoSolicitud.ABIERTA, LocalDateTime.now(), c);
		Tecnico t = new Tecnico("Jossue", false, "");
		assertThrows(IllegalArgumentException.class, () -> {
			s.asignarTecnico(t);
		});
	}
	@Test
	void noDesactivarTecnicoYaInactivo() {
		Tecnico t = new Tecnico("Fran", false, "");
		assertThrows(IllegalStateException.class, () -> t.desactivar());
	}
	@Test
	void testCambioEspecialidadCorrecto() {
	    Tecnico t = new Tecnico("Fran", true, "Java");
	    t.actualizarEspecialidad("Python");
	    assertEquals("Python", t.getEspecialidad());
	}

	@Test
	void ExcepcionCambioEspecialidad() {
	    Tecnico t = new Tecnico("Fran", true, "Java");
	    
	    // Error 1: Misma especialidad
	    assertThrows(IllegalArgumentException.class, () -> t.actualizarEspecialidad("Java"));
	    
	    // Error 2: Técnico inactivo
	    t.desactivar();
	    assertThrows(IllegalStateException.class, () -> t.actualizarEspecialidad("C++"));
	}
	
	@Test
	void testErrorAlDesactivarSeguridadSinEmergencia() {
	    Tecnico t = new Tecnico("Fran", true, "Seguridad");
	    assertThrows(IllegalStateException.class, () -> t.desactivarSeguro(false));
	}

	@Test
	void testDesactivarSeguridadConEmergencia() {
	    Tecnico t = new Tecnico("Fran", true, "Seguridad");
	    t.desactivarSeguro(true);
	    assertFalse(t.isActivo());
	}
}
