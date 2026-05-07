package com.mgcss.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime fechaCreacion;
	
	@ElementCollection
	@CollectionTable(name = "solicitud_historial", joinColumns = @JoinColumn(name = "solicitud_id"))
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private List<EstadoSolicitud> historialEstados = new ArrayList<>();
	public enum EstadoSolicitud {
		ABIERTA, EN_PROCESO, CERRADA
	}

	@Enumerated(EnumType.STRING)
	private EstadoSolicitud estado;
	private String descripcion;
	private LocalDateTime fechaCierre;
	private LocalDateTime SLA;
	private boolean urgente;
	@ManyToOne
	private Tecnico tecnico;
	@ManyToOne (cascade = CascadeType.PERSIST)
	private Cliente cliente;

	public Solicitud() {
	}

	public Solicitud(String descripcion, EstadoSolicitud estado, LocalDateTime fechaCreacion, Cliente cliente) {

		if (estado == null) {
            throw new IllegalArgumentException("Estado obligatorio");
        }

        if (fechaCreacion == null) {
            throw new IllegalArgumentException("Fecha obligatoria");
        }

        if (fechaCreacion.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Fecha no puede ser futura");
        }

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente obligatorio");
        }

        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.historialEstados.add(estado);
        if(this.cliente.getTipoCliente()==Cliente.TipoCliente.PREMIUM) {
        	this.SLA=this.fechaCreacion.plusHours(48);
        }
        else {
        	this.SLA=this.fechaCreacion.plusHours(96);
        }
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
			throw new IllegalStateException("Solo se puede cerrar si está en proceso");
		}
		cambiarEstado(EstadoSolicitud.CERRADA);
		this.fechaCierre = LocalDateTime.now();
	}

	public void iniciarProceso() {
		if (this.estado != EstadoSolicitud.ABIERTA) {
			throw new IllegalStateException("Solo se puede iniciar si está ABIERTA");
		}
		cambiarEstado(EstadoSolicitud.EN_PROCESO);
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void asignarTecnico(Tecnico t) {
		if (this.estado == EstadoSolicitud.CERRADA) {
			throw new IllegalArgumentException("No se puede asignar un tecnico a una solicitud cerrada");
		}
		
		if (this.tecnico != null) {
            throw new IllegalStateException("La solicitud ya tiene un tecnico asignado");
        }

		if (t == null || !t.isActivo()) {
			throw new IllegalArgumentException("No se puede asignar un tecnico inactivo");
		}

		this.tecnico = t;

	}
	
	public void reabrir() {
		if(this.estado != EstadoSolicitud.CERRADA)
			throw new IllegalStateException("Solo se pueden reabrir solicitudes cerradas");
		cambiarEstado(EstadoSolicitud.EN_PROCESO);
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
	
	public void marcarComoUrgente() {
	    if (this.urgente) {
	        throw new IllegalStateException("La solicitud ya tiene prioridad urgente.");
	    }
	    if (this.estado == EstadoSolicitud.CERRADA) {
	        throw new IllegalStateException("No se puede dar prioridad a una solicitud cerrada.");
	    }
	    
	    boolean esPremium = cliente.getTipoCliente() == Cliente.TipoCliente.PREMIUM;

        if (esPremium) {
            this.urgente = true;
        } else {
	        // Si no es premium, se necesita que la descripción sea larga para ser urgente
	        if (this.descripcion != null && this.descripcion.length() > 30) {
	            this.urgente = true;
	        } else {
	            throw new IllegalArgumentException("Descripción insuficiente para prioridad urgente");
	        }
	    }
	}
	
	public List<EstadoSolicitud> getHistorialEstados() {
		return new ArrayList<>(historialEstados);
	}
	
	private void cambiarEstado(EstadoSolicitud nuevoEstado) {
	    this.estado = nuevoEstado;
	    this.historialEstados.add(nuevoEstado);
	}

}