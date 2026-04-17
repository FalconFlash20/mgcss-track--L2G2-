package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
