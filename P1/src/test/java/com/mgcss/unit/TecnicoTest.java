package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Tecnico.Especialidad;


public class TecnicoTest {
	@Test
    public void crearTecnicoCorrecto() {
        Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);

        assertEquals("Fran", t.getNombre());
        assertTrue(t.isActivo());
        assertEquals(Especialidad.SOFTWARE, t.getEspecialidad());
    }
	
	@Test
    public void errorCrearTecnico() {
        // nombre inválido
        assertThrows(IllegalArgumentException.class, () -> new Tecnico(null, true, Especialidad.SOFTWARE));

        // especialidad null
        assertThrows(IllegalArgumentException.class, () -> new Tecnico("Fran", true, null));
    }
	
	@Test
    public void activarCorrectamente() {
        Tecnico t = new Tecnico("Fran", false, Especialidad.SOFTWARE);

        t.activar();

        assertTrue(t.isActivo());
    }

    @Test
    public void activarYaActivo() {
        Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);

        assertThrows(IllegalStateException.class, t::activar);
    }

    @Test
    public void desactivarCorrectamente() {
        Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);

        t.desactivar();

        assertFalse(t.isActivo());
    }
    
	@Test
	public void desactivarTecnicoYaInactivo() {
		Tecnico t = new Tecnico("Fran", false, Especialidad.SOFTWARE);
		assertThrows(IllegalStateException.class, t::desactivar);
	}
	@Test
	public void cambioEspecialidadCorrecto() {
	    Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);
	    t.actualizarEspecialidad(Especialidad.HARDWARE);
	    assertEquals(Especialidad.HARDWARE, t.getEspecialidad());
	}

	@Test
	public void errorCambioEspecialidad() {
	    Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);
	    
	 // misma especialidad
        assertThrows(IllegalArgumentException.class, () -> t.actualizarEspecialidad(Especialidad.SOFTWARE));

        // especialidad null
        assertThrows(IllegalArgumentException.class, () -> t.actualizarEspecialidad(null));

        // técnico inactivo
        t.desactivar();
        assertThrows(IllegalStateException.class, () -> t.actualizarEspecialidad(Especialidad.REDES));
	}
	
	@Test
	public void desactivarSeguridadSinEmergencia() {
	    Tecnico t = new Tecnico("Fran", true, Especialidad.SEGURIDAD);
	    assertThrows(IllegalStateException.class, () -> t.desactivarSeguro(false));
	}

	@Test
	public void desactivarSeguridadConEmergencia() {
	    Tecnico t = new Tecnico("Fran", true, Especialidad.SEGURIDAD);
	    t.desactivarSeguro(true);
	    assertFalse(t.isActivo());
	}
}
