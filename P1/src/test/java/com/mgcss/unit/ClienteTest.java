package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.Cliente.TipoCliente;

public class ClienteTest {
	
    private Cliente clienteStandard() {
        return new Cliente(1L, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
    }

    private Cliente clientePremium() {
        return new Cliente(1L, "Empresa", "empresa@test.com", TipoCliente.PREMIUM);
    }
	
    @Test
    public void premiumTienePrioridad() {
        assertTrue(clientePremium().tienePrioridad());
    }

    @Test
    public void estandarSinPrioridad() {
        assertFalse(clienteStandard().tienePrioridad());
    }

    @Test
    public void ascenderClienteCorrecto() {
        Cliente c = new Cliente(1L, "Pepe", "pepe@empresa.org", TipoCliente.STANDARD);

        c.ascenderCliente();

        assertEquals(TipoCliente.PREMIUM, c.getTipoCliente());
    }

    @Test
    public void errorAscenderCliente() {
        Cliente c = clienteStandard();

        c.ascenderCliente();

        assertEquals(TipoCliente.STANDARD, c.getTipoCliente());
    }

    @Test
    public void errorAscenderSiYaEsPremium() {
        Cliente c = clientePremium();

        assertThrows(IllegalStateException.class, () -> c.ascenderCliente());
    }

    @Test
    public void bloquearClienteStandard() {
        Cliente c = clienteStandard();

        c.bloquearCuenta();

        assertTrue(c.isBloqueado());
    }

    @Test
    public void errorBloquearClientePremium() {
        Cliente c = clientePremium();

        assertThrows(IllegalStateException.class, c::bloquearCuenta);
    }

    @Test
    public void desbloquearCorrectamente() {
        Cliente c = clienteStandard();
        c.bloquearCuenta();

        c.desbloquearCuenta();

        assertFalse(c.isBloqueado());
    }

	@Test
	public void ErrorAlDesbloquearCuentaNoBloqueada() {
		Cliente c = clienteStandard();
		assertThrows(IllegalStateException.class, () -> c.desbloquearCuenta());
	}
	
	@Test
	public void verificarConExito() {
		Cliente c = clienteStandard();
		c.verificarIdentidad();
	    assertTrue(c.isVerificado());
	}

	@Test
    public void errorVerificarEmailInvalido() {
        Cliente c = new Cliente(1L, "Pepe", "correo", TipoCliente.STANDARD);

        assertThrows(IllegalArgumentException.class, c::verificarIdentidad);
    }

    @Test
    public void errorVerificarDosVeces() {
        Cliente c = clienteStandard();

        c.verificarIdentidad();

        assertThrows(IllegalStateException.class, c::verificarIdentidad);
    }
	
	@Test
    public void actualizarDatosCompleto() {
        Cliente c = clienteStandard();

        c.actualizarDatos("Nuevo", "nuevo@test.com");

        assertEquals("Nuevo", c.getNombre());
        assertEquals("nuevo@test.com", c.getEmail());
    }

    @Test
    public void actualizarSoloNombre() {
        Cliente c = clienteStandard();

        c.actualizarDatos("Nuevo", null);

        assertEquals("Nuevo", c.getNombre());
        assertEquals("pepe@test.com", c.getEmail());
    }

    @Test
    public void actualizarSoloEmail() {
        Cliente c = clienteStandard();

        c.actualizarDatos(" ", "nuevo@test.com");

        assertEquals("Pepe", c.getNombre());
        assertEquals("nuevo@test.com", c.getEmail());
    }
    
    @Test
    public void noActualizarDatosInvalidos() {
        Cliente c = clienteStandard();

        c.actualizarDatos(null, "");

        assertEquals("Pepe", c.getNombre());
        assertEquals("pepe@test.com", c.getEmail());
    }
    
    @Test
    public void clienteNombreNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(1L, null, "test@test.com", TipoCliente.STANDARD));
    }

    @Test
    public void clienteEmailNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(1L, "Pepe", null, TipoCliente.STANDARD));
    }

    @Test
    public void clienteTipoNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(1L, "Pepe", "test@test.com", null));
    }
}
