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
	
}
