package com.mgcss.api.DTO;

public class TecnicoRequestDTO {
	private String nombre;
	private String especialidad;
	
	public TecnicoRequestDTO() {
		
	}
	
	public TecnicoRequestDTO(String nombre, String especialidad) {
		this.nombre = nombre;
		this.especialidad = especialidad;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}
}
