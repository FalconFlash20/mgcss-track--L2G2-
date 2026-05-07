package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Tecnico.Especialidad;


public class TecnicoTest {
	@Test
     void crearTecnicoCorrecto() {
        Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);

        assertEquals("Fran", t.getNombre());
        assertTrue(t.isActivo());
        assertEquals(Especialidad.SOFTWARE, t.getEspecialidad());
    }
	
	@Test
     void errorCrearTecnico() {
        // nombre inválido
        assertThrows(IllegalArgumentException.class, () -> new Tecnico(null, true, Especialidad.SOFTWARE));

        // especialidad null
        assertThrows(IllegalArgumentException.class, () -> new Tecnico("Fran", true, null));
    }
	
	@Test
     void activarCorrectamente() {
        Tecnico t = new Tecnico("Fran", false, Especialidad.SOFTWARE);

        t.activar();

        assertTrue(t.isActivo());
    }

    @Test
     void activarYaActivo() {
        Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);

        assertThrows(IllegalStateException.class, t::activar);
    }

    @Test
     void desactivarCorrectamente() {
        Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);

        t.desactivar();

        assertFalse(t.isActivo());
    }
    
	@Test
	 void desactivarTecnicoYaInactivo() {
		Tecnico t = new Tecnico("Fran", false, Especialidad.SOFTWARE);
		assertThrows(IllegalStateException.class, t::desactivar);
	}
	@Test
	 void cambioEspecialidadCorrecto() {
	    Tecnico t = new Tecnico("Fran", true, Especialidad.SOFTWARE);
	    t.actualizarEspecialidad(Especialidad.HARDWARE);
	    assertEquals(Especialidad.HARDWARE, t.getEspecialidad());
	}

	@Test
	 void errorCambioEspecialidad() {
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
	 void desactivarSeguridadSinEmergencia() {
	    Tecnico t = new Tecnico("Fran", true, Especialidad.SEGURIDAD);
	    assertThrows(IllegalStateException.class, () -> t.desactivarSeguro(false));
	}

	@Test
	 void desactivarSeguridadConEmergencia() {
	    Tecnico t = new Tecnico("Fran", true, Especialidad.SEGURIDAD);
	    t.desactivarSeguro(true);
	    assertFalse(t.isActivo());
	}
	
}
