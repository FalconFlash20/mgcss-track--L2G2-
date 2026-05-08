package com.mgcss.api.DTO;

import com.mgcss.domain.Tecnico.Especialidad;

public class TecnicoResponseDTO {
	private Long id;
	private String nombre;
	private boolean activo;
	private String especialidad;

	public TecnicoResponseDTO(Long id, String nombre, boolean activo, String especialidad) {
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.especialidad = especialidad;
	}
	public Long Id() {
		return id;
	}
	public String Nombre() {
		return nombre;
	}
	public boolean Activo() {
		return activo;
	}
	public String Especialidad() {
		return especialidad;
	}	
}
