package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.Cliente.TipoCliente;

public class ClienteTest {
	
	@Test
	public void PremuimConPrioridad() {
		Cliente c=new Cliente(1L,"Pepe","pepe@gmail.com",TipoCliente.PREMIUM);
		assertTrue(c.TienePrioridad());
	}
	@Test
	public void EstandarSinPrioridad() {
		Cliente c=new Cliente(1L,"Pepe","pepe@gmail.com",TipoCliente.STANDARD);
		assertFalse(c.TienePrioridad());
	}
	@Test
	public void AscensoaPremium() {
		Cliente c=new Cliente(1L,"Pepe","pepe@gmail.edu",TipoCliente.STANDARD);
		c.ascenderCliente();
		assertEquals(TipoCliente.PREMIUM,c.getTipoCliente());
	}
	@Test
	void testFalloAscensoPremium() {
	    // Caso 1: Email normal no asciende
	    Cliente c1 = new Cliente(1L, "Pepe", "pepe@gmail.com", TipoCliente.STANDARD);
	    c1.ascenderCliente();
	    assertEquals(TipoCliente.STANDARD, c1.getTipoCliente());

	    // Caso 2: Ya es premium
	    Cliente c2 = new Cliente(2L, "Ana", "ana@org.org", TipoCliente.PREMIUM);
	    assertThrows(IllegalStateException.class, () -> c2.ascenderCliente());
	}
}
