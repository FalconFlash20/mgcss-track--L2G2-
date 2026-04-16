package com.mgcss.unit;

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

@Test
void cerrarSolicitudEnProceso() {
    Solicitud s = new Solicitud(4L, EstadoSolicitud.EN_PROCESO, LocalDateTime.now());

    s.cerrar();

    assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
}

@Test
void cambiarSolicitudAbiertaAEnProceso() {
    Solicitud s = new Solicitud(5L, EstadoSolicitud.ABIERTA, LocalDateTime.now());
    s.iniciarProceso();
    assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
}

@Test
void noCerrarSolicitudYaCerrada() {
    Solicitud s = new Solicitud(6L, EstadoSolicitud.CERRADA, LocalDateTime.now());

    assertThrows(IllegalStateException.class, () -> s.cerrar());
}

@Test
void noIniciarProcesoSiYaEstaEnProceso() {
    Solicitud s = new Solicitud(7L, EstadoSolicitud.EN_PROCESO, LocalDateTime.now());

    assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
}

@Test
void noIniciarProcesoSiEstaCerrada() {
    Solicitud s = new Solicitud(8L, EstadoSolicitud.CERRADA, LocalDateTime.now());

    assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
}

@Test
void noCrearSolicitudConIdNegativo() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Solicitud(-1L, EstadoSolicitud.ABIERTA, LocalDateTime.now());
    });
}

@Test
void noCrearSolicitudSinEstado() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Solicitud(1L, null, LocalDateTime.now());
    });
}

@Test
void noCrearSolicitudSinFecha() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Solicitud(1L, EstadoSolicitud.ABIERTA, null);
    });
}

@Test
void noCrearSolicitudConFechaFutura() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Solicitud(1L, EstadoSolicitud.ABIERTA, LocalDateTime.now().plusDays(1));
    });
}

@Test
void noDesactivarTecnicoYaInactivo() {
    Tecnico t = new Tecnico("Fran", false);
    assertThrows(IllegalStateException.class, () -> t.desactivar());
}

@Test
void noAsignarTecnicoNull() {
    Solicitud s = new Solicitud(1L, EstadoSolicitud.ABIERTA, LocalDateTime.now());

    assertThrows(IllegalArgumentException.class, () -> s.asignarTecnico(null));
}
}
