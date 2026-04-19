package com.mgcss.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime fechaCreacion;

	public enum EstadoSolicitud {
		ABIERTA, EN_PROCESO, CERRADA
	}

	@Enumerated(EnumType.STRING)
	private EstadoSolicitud estado;
	private String descripcion;
	private LocalDateTime fechaCierre;
	private boolean urgente;
	@Transient
	private Tecnico tecnico;
	@Transient
	private Cliente cliente;

	public Solicitud() {
	}

	public Solicitud(Long id, String descripcion, EstadoSolicitud estado, LocalDateTime fechaCreacion,
			Cliente cliente) {

		if (id != null && id < 0) {
			throw new IllegalArgumentException("ID inválido");
		}

		if (estado == null) {
			throw new IllegalArgumentException("Estado obligatorio");
		}

		if (fechaCreacion == null) {
			throw new IllegalArgumentException("Fecha obligatoria");
		}

		if (fechaCreacion.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Fecha no puede ser futura");
		}
		

		this.id = id;
		this.descripcion = descripcion;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	
	public boolean isUrgente() {
		return urgente;
	}

	public void cerrar() {
		if (this.estado != EstadoSolicitud.EN_PROCESO) {
			throw new IllegalStateException("Solo se puede cerrar solicitudes si no está en proceso ");
		}
		this.estado = EstadoSolicitud.CERRADA;

	}

	public void iniciarProceso() {
		if (this.estado != EstadoSolicitud.ABIERTA) {
			throw new IllegalStateException("Solo se puede iniciar si está ABIERTA");
		}
		this.estado = EstadoSolicitud.EN_PROCESO;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void asignarTecnico(Tecnico t) {
		if (this.estado == EstadoSolicitud.CERRADA) {
			throw new IllegalArgumentException("No se puede asignar un tecnico a una solicitud cerrada");
		}

		if (t == null || !t.isActivo()) {
			throw new IllegalArgumentException("No se puede asignar un tecnico inactivo");
		}

		this.tecnico = t;

	}
	
	public void reabrirSolicitud() {
		if(this.estado!= EstadoSolicitud.CERRADA)
			throw new IllegalStateException("Solo se pueden reabrir solicitudes cerradas");
		this.estado=EstadoSolicitud.ABIERTA;
		this.tecnico=null;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public Cliente getCliente() {
		return cliente;
	}
	// RN: Una solicitud solo se puede marcar como urgente si está ABIERTA o EN_PROCESO.
	// Además, si el cliente es PREMIUM, se marca como urgente automáticamente.
	// Si ya es urgente, no se puede volver a marcar .
	public void marcarComoUrgente() {
	    if (this.urgente) {
	        throw new IllegalStateException("La solicitud ya tiene prioridad urgente.");
	    }
	    if (this.estado == EstadoSolicitud.CERRADA) {
	        throw new IllegalStateException("No se puede dar prioridad a una solicitud cerrada.");
	    }
	    
	    if (this.cliente != null && this.cliente.getTipoCliente() == Cliente.TipoCliente.PREMIUM) {
	        this.urgente = true;
	    } else {
	        // Si no es premium, necesitamos que la descripción sea larga para ser urgente
	        if (this.descripcion != null && this.descripcion.length() > 20) {
	            this.urgente = true;
	        } else {
	            throw new IllegalArgumentException("Descripción demasiado corta para prioridad urgente en clientes estándar.");
	        }
	    }
	}

}