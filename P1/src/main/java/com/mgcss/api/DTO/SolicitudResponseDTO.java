package com.mgcss.api.DTO;

import java.time.LocalDateTime;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Tecnico;

public class SolicitudResponseDTO {

    private final Long id;
    private final String descripcion;
    private final String estado;
    private LocalDateTime fechaCierre;
	private LocalDateTime tiempoej;
	private boolean urgente;
	private Tecnico tecnico;
	private Cliente cliente;
	
	public SolicitudResponseDTO(Long id, String descripcion, String estado, LocalDateTime fechaCierre,
			LocalDateTime tiempoej, boolean urgente, Tecnico tecnico, Cliente cliente) {
		this.id = id;
		this.descripcion = descripcion;
		this.estado = estado;
		this.fechaCierre = fechaCierre;
		this.tiempoej = tiempoej;
		this.urgente = urgente;
		this.tecnico = tecnico;
		this.cliente = cliente;
	}

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public LocalDateTime getTiempoej() {
		return tiempoej;
	}

	public boolean getUrgente() {
		return urgente;
	}


	public Tecnico getTecnico() {
		return tecnico;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public Long getId() {
		return id;
	}


	public String getEstado() {
		return estado;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	

}