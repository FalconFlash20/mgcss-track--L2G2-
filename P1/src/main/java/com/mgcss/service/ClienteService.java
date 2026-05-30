package com.mgcss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mgcss.domain.Cliente;
import com.mgcss.infrastructure.persistence.JpaClienteRepository;

@Service
public class ClienteService {
	private final JpaClienteRepository clienteRepository;

	public ClienteService(JpaClienteRepository cliente) {
		this.clienteRepository = cliente;
	}
	public Cliente crearCliente(Cliente c) {
		return clienteRepository.save(c);
	}
	public Cliente consultarCliente(Long cId) {
    	return clienteRepository.findById(cId).orElseThrow(() -> new IllegalArgumentException("No se ha podido consultar el cliente"));		
	}
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	public void ascender(Long id) {
		Cliente c=consultarCliente(id);
		c.ascenderCliente();
		clienteRepository.save(c);
	}
	public void bloquear(Long id) {
		Cliente c=consultarCliente(id);
		c.bloquearCuenta();
		clienteRepository.save(c);
	}
	public void desbloquear(Long id) {
		Cliente c=consultarCliente(id);
		c.desbloquearCuenta();
		clienteRepository.save(c);
	}
	
	public Map<String, Object> obtenerMetricas() {
	    List<Cliente> clientes = clienteRepository.findAll();
	    Map<String, Object> metricas = new HashMap<>();

	    metricas.put("totalClientes", clientes.size());
	    metricas.put("premium", clientes.stream().filter(c -> c.getTipoCliente() == Cliente.TipoCliente.PREMIUM).count());
	    metricas.put("standard", clientes.stream().filter(c -> c.getTipoCliente() == Cliente.TipoCliente.STANDARD).count());
	    metricas.put("bloqueados", clientes.stream().filter(Cliente::isBloqueado).count());
	    metricas.put("verificados", clientes.stream().filter(Cliente::isVerificado).count());

	    return metricas;
	}
}
