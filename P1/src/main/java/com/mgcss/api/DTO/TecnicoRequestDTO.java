package com.mgcss.api.DTO;

public class TecnicoRequestDTO {
	private String nombre;
	private String especialidad;
	
	public TecnicoRequestDTO(String nombre, String especialidad) {
		super();
		this.nombre = nombre;
		this.especialidad = especialidad;
	}

	public String Nombre() {
		return nombre;
	}

	public String Especialidad() {
		return especialidad;
	}
}
