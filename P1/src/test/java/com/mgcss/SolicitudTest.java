package com.mgcss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.domain.Tecnico;

public class SolicitudTest {
@Test
void noCerrarEnProceso() {
	Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.ABIERTA, LocalDateTime.now());
	assertThrows(IllegalStateException.class, () -> {solicitud.cerrar();});
}

@Test
void asignaTecnicoActivo() {
	Solicitud s = new Solicitud(2L, EstadoSolicitud.ABIERTA, LocalDateTime.now());
	Tecnico t = new Tecnico("Francisco", true);
	
	s.asignarTecnico(t);
	assertEquals(t, s.getTecnico());
}

@Test
void asignaTecnicoInactivo() {
	Solicitud s = new Solicitud(3L, EstadoSolicitud.ABIERTA, LocalDateTime.now());
	Tecnico t = new Tecnico("Jossue", false);
	assertThrows(IllegalArgumentException.class, () -> { s.asignarTecnico(t); });
}
}
