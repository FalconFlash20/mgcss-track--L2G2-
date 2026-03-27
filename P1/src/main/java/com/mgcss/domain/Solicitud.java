package com.mgcss.domain;

import java.time.LocalDateTime;

public class Solicitud {

    private Long id;
    private EstadoSolicitud estado;
    private LocalDateTime fechaCreacion;
    public enum EstadoSolicitud{ ABIERTA, EN_PROCESO, CERRADA };

    public Solicitud() {
    }

    public Solicitud(Long id, EstadoSolicitud estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

	public void cerrar() {
		if(this.estado != EstadoSolicitud.EN_PROCESO) {
			throw new IllegalStateException("Solo se puede cerrar solicitudes si no está en proceso ");
		}
	this.estado = EstadoSolicitud.CERRADA;
		
	}
}