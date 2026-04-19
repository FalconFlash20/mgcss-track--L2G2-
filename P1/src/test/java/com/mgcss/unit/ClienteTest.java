package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.Cliente.TipoCliente;

public class ClienteTest {

	@Test
	public void PremuimConPrioridad() {
		Cliente c = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.PREMIUM);
		assertTrue(c.TienePrioridad());
	}

	@Test
	public void EstandarSinPrioridad() {
		Cliente c = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
		assertFalse(c.TienePrioridad());
	}

	@Test
	public void AscensoaPremium() {
		Cliente c = new Cliente(1L, "Pepe", "pepe@gmail.edu", TipoCliente.STANDARD);
		c.ascenderCliente();
		assertEquals(TipoCliente.PREMIUM, c.getTipoCliente());
	}

	@Test
	public void testFalloAscensoPremium() {
		// Caso 1: Email normal no asciende
		Cliente c1 = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
		c1.ascenderCliente();
		assertEquals(TipoCliente.STANDARD, c1.getTipoCliente());

		// Caso 2: Ya es premium
		Cliente c2 = new Cliente(2L, "Ana", "ana@org.org", TipoCliente.PREMIUM);
		assertThrows(IllegalStateException.class, () -> c2.ascenderCliente());
	}

	@Test
	public void testBloqueoClienteStandardExitoso() {
		Cliente c = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
		c.bloquearCuenta();
		assertTrue(c.isBloqueado());
	}

	@Test
	public void ErrorAlBloquearClientePremium() {
		Cliente c = new Cliente(1L, "Empresa VIP", "vip@empresa.com", TipoCliente.PREMIUM);

		assertThrows(IllegalStateException.class, () -> c.bloquearCuenta());
	}

	@Test
	public void testDesbloqueoExitoso() {
		Cliente c = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
		c.bloquearCuenta(); // Lo bloqueamos primero

		c.desbloquearCuenta();
		assertFalse(c.isBloqueado());
	}

	@Test
	public void ErrorAlDesbloquearCuentaYaActiva() {
		Cliente c = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
		assertThrows(IllegalStateException.class, () -> c.desbloquearCuenta());
	}
	@Test
	public void ErrorAlDesbloquearClienteSinNombre() {
		Cliente c = new Cliente(1L, null, "pepe@gmail.com", TipoCliente.STANDARD);
		c.bloquearCuenta();
		assertThrows(IllegalArgumentException.class, ()->c.desbloquearCuenta());
	}
	@Test
	public void VerificacionExitosa() {
	    Cliente c = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
	    c.verificarIdentidad();
	    assertTrue(c.isVerificado());
	}

	@Test
	public void ErrorVerificacion() {
		// Prueba email null
	    Cliente c1 = new Cliente(1L, "Pepe", null, TipoCliente.STANDARD);
	    assertThrows(IllegalArgumentException.class, () -> c1.verificarIdentidad());
	    
	    // Prueba email sin arroba
	    Cliente c2 = new Cliente(2L, "Pepe", "gmail.com", TipoCliente.STANDARD);
	    assertThrows(IllegalArgumentException.class, () -> c2.verificarIdentidad());
	    Cliente c3 = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
	    c3.verificarIdentidad();
	    assertThrows(IllegalStateException.class, () -> c3.verificarIdentidad());
	}
}
