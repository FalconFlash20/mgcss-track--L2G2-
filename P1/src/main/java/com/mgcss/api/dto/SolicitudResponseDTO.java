package com.mgcss.api.dto;

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
	
	public SolicitudResponseDTO(Builder b) {
		this.id = b.id;
		this.descripcion = b.descripcion;
		this.estado = b.estado;
		this.fechaCierre = b.fechaCierre;
		this.tiempoej = b.tiempoej;
		this.urgente = b.urgente;
		this.tecnico = b.tecnico;
		this.cliente = b.cliente;
	}
		
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

	
	public static class Builder {
        private Long id;
        private String descripcion;
        private String estado;
        private LocalDateTime fechaCierre;
        private LocalDateTime tiempoej;
        private boolean urgente;
        private Tecnico tecnico;
        private Cliente cliente;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public Builder estado(String estado) { this.estado = estado; return this; }
        public Builder fechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; return this; }
        public Builder tiempoej(LocalDateTime tiempoej) { this.tiempoej = tiempoej; return this; }
        public Builder urgente(boolean urgente) { this.urgente = urgente; return this; }
        public Builder tecnico(Tecnico tecnico) { this.tecnico = tecnico; return this; }
        public Builder cliente(Cliente cliente) { this.cliente = cliente; return this; }

        public SolicitudResponseDTO build() {
            return new SolicitudResponseDTO(this);
        }
    }

}