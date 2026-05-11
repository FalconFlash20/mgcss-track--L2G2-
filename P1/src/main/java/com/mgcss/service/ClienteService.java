package com.mgcss.service;

import java.util.List;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;

public class ClienteService {
	private final ClienteRepository clienterepository;

	public ClienteService(ClienteRepository cliente) {
		this.clienterepository = cliente;
	}
	public void crearCliente(Cliente c) {
		clienterepository.save(c);
	}
	public Cliente consultarTecnico(Long ClienteId) {
    	return clienterepository.findById(ClienteId).orElseThrow(() -> new IllegalArgumentException("No se ha podido consultar el cliente"));		
	}
	public List<Cliente> listarTecnicos() {
		return clienterepository.findAll();
	}
	
}
