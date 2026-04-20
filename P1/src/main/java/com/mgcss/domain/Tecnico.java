package com.mgcss.domain;

import jakarta.persistence.*;

@Entity
public class Tecnico {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private boolean activo;
	@Enumerated(EnumType.STRING)
	private Especialidad especialidad;
	public enum Especialidad {
        HARDWARE,
        SOFTWARE,
        REDES,
        SEGURIDAD,
        SOPORTE_GENERAL
    }
	public Tecnico() {}
	
	public Tecnico(String nombre, boolean activo, Especialidad especialidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }
        if (especialidad == null) {
            throw new IllegalArgumentException("Especialidad obligatoria");
        }

        this.nombre = nombre;
        this.activo = activo;
        this.especialidad = especialidad;
    }
	
	public boolean isActivo() {
		return activo;
	}
	
	public void activar() {
        if (activo) {
            throw new IllegalStateException("Ya está activo");
        }
        this.activo = true;
    }
	
	public void desactivar() {
	    if (!activo) {
	        throw new IllegalStateException("Ya está inactivo");
	    }
	    this.activo = false;
	}

	public String getNombre() {
		return nombre;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void actualizarEspecialidad(Especialidad nuevaEspecialidad) {
	    if (!this.activo) {
	        throw new IllegalStateException("No se puede cambiar especialidad de un técnico inactivo");
	    }
	    if (nuevaEspecialidad == null) {
            throw new IllegalArgumentException("Especialidad inválida");
        }
	    if (this.especialidad == nuevaEspecialidad) {
	        throw new IllegalArgumentException("La nueva especialidad debe ser distinta a la actual");
	    }
	    this.especialidad = nuevaEspecialidad;
	}

	public void desactivarSeguro(boolean esEmergencia) {
	    if (this.especialidad == Especialidad.SEGURIDAD && !esEmergencia) {
	        throw new IllegalStateException("No se puede desactivar personal de Seguridad sin modo emergencia");
	    }
	    this.activo = false;
	}
	
}
