package com.mgcss.domain;

public class Tecnico {
	private String nombre;
	private boolean activo;
	public Tecnico(String nombre, boolean activo) {
		this.nombre = nombre;
		this.activo = activo;
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
}
