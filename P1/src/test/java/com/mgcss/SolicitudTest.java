package com.mgcss;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;

public class SolicitudTest {
@Test
void noCerrarEnProceso() {
	Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.ABIERTA, LocalDateTime.now());
	assertThrows(IllegalStateException.class, () -> {solicitud.cerrar();});
}
}
