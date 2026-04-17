package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	
}
