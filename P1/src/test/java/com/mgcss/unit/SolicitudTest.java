package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Tecnico.Especialidad;
import com.mgcss.domain.Cliente.TipoCliente;

public class SolicitudTest {
	
	private Cliente cliente() {
        return new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
    }

    private Tecnico tecnicoActivo() {
        return new Tecnico("Fran", true, Especialidad.SOFTWARE);
    }

    private Tecnico tecnicoInactivo() {
        return new Tecnico("Alejandro", false, Especialidad.SOFTWARE);
    }
	@Test
	public void noCerrarEnProceso() {
		Solicitud solicitud = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());
		assertThrows(IllegalStateException.class, () -> { 
			solicitud.cerrar();
		});
	}

	@Test
	public void asignaTecnicoActivo() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());
		Tecnico t = tecnicoActivo();

        s.asignarTecnico(t);

        assertEquals(t, s.getTecnico());
	}
	@Test
	public void asignaTecnicoInactivo() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());
		Tecnico t = tecnicoInactivo();
		assertThrows(IllegalArgumentException.class, () -> {
			s.asignarTecnico(t);
		});
	}
	
	@Test
    public void noAsignarTecnicoSiYaTieneUno() {
        Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());
        Tecnico t = tecnicoActivo();
        s.asignarTecnico(t);

        assertThrows(IllegalStateException.class, () -> s.asignarTecnico(t));
    }

    @Test
    public void noAsignarTecnicoSiSolicitudCerrada() {
        Solicitud s = new Solicitud("desc", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente());
        Tecnico t = tecnicoActivo();
        s.cerrar();

        assertThrows(IllegalArgumentException.class, () -> s.asignarTecnico(t));
    }

	@Test
    public void iniciarProcesoCorrectamente() {
        Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());

        s.iniciarProceso();

        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
    }
	
	@Test
    public void noIniciarProcesoSiNoAbierta() {
        Solicitud s = new Solicitud("desc", EstadoSolicitud.CERRADA, LocalDateTime.now(), cliente());
        assertThrows(IllegalStateException.class, () -> {
            s.iniciarProceso();
        });
    }
	
	@Test
	public void cerrarSolicitudEnProceso() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente());
		s.cerrar();
		assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
		assertNotNull(s.getFechaCierre());
	}

	@Test
	public void noCerrarSolicitudYaCerrada() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.CERRADA, LocalDateTime.now(), cliente());
		assertThrows(IllegalStateException.class, () -> s.cerrar());
	}

	@Test
	public void noIniciarProcesoSiYaEstaEnProceso() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente());
		assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
	}

	@Test
	public void noIniciarProcesoSiEstaCerrada() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.CERRADA, LocalDateTime.now(), cliente());
		assertThrows(IllegalStateException.class, () -> s.iniciarProceso());
	}

	@Test
	public void noCrearSolicitudSinEstado() {
		LocalDateTime fecha = LocalDateTime.now();
		Cliente c = cliente();
		assertThrows(IllegalArgumentException.class, () -> {  new Solicitud("desc", null, fecha, c); });
	}

	@Test
	public void noCrearSolicitudSinFecha() {
		Cliente c = cliente();
		assertThrows(IllegalArgumentException.class, () -> { new Solicitud("desc", EstadoSolicitud.ABIERTA, null, c); });
	}

	@Test
	public void noCrearSolicitudConFechaFutura() {
		LocalDateTime fechaFutura = LocalDateTime.now().plusDays(1);
		Cliente c = cliente();
		assertThrows(IllegalArgumentException.class, () -> { new Solicitud("desc", EstadoSolicitud.ABIERTA, fechaFutura, c); });
	}

	@Test
	public void noAsignarTecnicoNull() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());
		assertThrows(IllegalArgumentException.class, () -> s.asignarTecnico(null));
	}
	
	@Test
	public void reabrirSolicitud() {
		Solicitud s = new Solicitud("desc", EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente());
        s.asignarTecnico(tecnicoActivo());
        s.cerrar();

        s.reabrirSolicitud();

        assertEquals(EstadoSolicitud.ABIERTA, s.getEstado());
        assertNull(s.getTecnico());
        assertNull(s.getFechaCierre());
	}
	
	@Test
	void marcarUrgenteClientePremium() {
	    Cliente vip = new Cliente(1L, "Empresa", "info@empresa.com", Cliente.TipoCliente.PREMIUM);
	    Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), vip);

        s.marcarComoUrgente();
        assertTrue(s.isUrgente());
	}

	@Test
    public void marcarUrgenteClienteEstandar() {
        Solicitud s = new Solicitud("Descripción suficientemente larga para ser urgente con cliente estandar",
                EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());

        s.marcarComoUrgente();

        assertTrue(s.isUrgente());
    }

    @Test
    public void errorUrgenteClienteEstandar() {
        Solicitud s = new Solicitud("corta", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());

        assertThrows(IllegalArgumentException.class, s::marcarComoUrgente);
    }
    
    @Test
    public void errorUrgenteSiYaEsUrgente() {
        Solicitud s = new Solicitud("Descripción suficientemente larga para ser urgente con cliente estandar",
                EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());

        s.marcarComoUrgente();

        assertThrows(IllegalStateException.class, s::marcarComoUrgente);
    }

    @Test
    public void errorUrgenteSiEstaCerrada() {
        Solicitud s = new Solicitud("Descripción suficientemente larga para ser urgente con cliente estandar",
                EstadoSolicitud.EN_PROCESO, LocalDateTime.now(), cliente());

        s.cerrar();

        assertThrows(IllegalStateException.class, s::marcarComoUrgente);
    }

    @Test
    public void errorClienteNull() {
    	LocalDateTime fecha = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Solicitud("desc", EstadoSolicitud.ABIERTA, fecha, null));
    }
	
}
