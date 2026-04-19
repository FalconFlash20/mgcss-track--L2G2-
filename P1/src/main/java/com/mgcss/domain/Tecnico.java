package com.mgcss.domain;

public class Tecnico {
	private String nombre;
	private boolean activo;
	private String especialidad;
	public Tecnico(String nombre, boolean activo,String especialidad) {
		this.nombre = nombre;
		this.activo = activo;
		this.especialidad=especialidad;
	}
	
	public boolean isActivo() {
		return activo;
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	// RN: Solo se puede cambiar la especialidad si el técnico está activo. 
	// Además, no puede ser la misma que ya tiene.
	public void actualizarEspecialidad(String nuevaEspecialidad) {
	    if (!this.activo) {
	        throw new IllegalStateException("No se puede cambiar especialidad de un técnico inactivo");
	    }
	    if (this.especialidad.equalsIgnoreCase(nuevaEspecialidad)) {
	        throw new IllegalArgumentException("La nueva especialidad debe ser distinta a la actual");
	    }
	    this.especialidad = nuevaEspecialidad;
	}
	
	// RN: No se puede desactivar a un técnico cuya especialidad sea "Seguridad" 
	// a menos que sea una emergencia (forzado).
	public void desactivarSeguro(boolean esEmergencia) {
	    if (this.especialidad.equalsIgnoreCase("Seguridad") && !esEmergencia) {
	        throw new IllegalStateException("No se puede desactivar personal de Seguridad sin modo emergencia");
	    }
	    this.activo = false;
	}
	
}
