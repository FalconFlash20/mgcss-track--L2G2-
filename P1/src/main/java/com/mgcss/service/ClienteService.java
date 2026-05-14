package com.mgcss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;

@Service
public class ClienteService {
	private final ClienteRepository clienterepository;

	public ClienteService(ClienteRepository cliente) {
		this.clienterepository = cliente;
	}
	public Cliente crearCliente(Cliente c) {
		return clienterepository.save(c);
	}
	public Cliente consultarCliente(Long ClienteId) {
    	return clienterepository.findById(ClienteId).orElseThrow(() -> new IllegalArgumentException("No se ha podido consultar el cliente"));		
	}
	public List<Cliente> listar() {
		return clienterepository.findAll();
	}
	public void ascender(Long id) {
		Cliente c=consultarCliente(id);
		c.ascenderCliente();
		clienterepository.save(c);
	}
	public void bloquear(Long id) {
		Cliente c=consultarCliente(id);
		c.bloquearCuenta();
		clienterepository.save(c);
	}
	public void desbloquear(Long id) {
		Cliente c=consultarCliente(id);
		c.desbloquearCuenta();
		clienterepository.save(c);
	}
	
}
