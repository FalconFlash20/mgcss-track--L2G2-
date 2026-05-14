package com.mgcss.api.DTO;

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
	public Long getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public boolean getActivo() {
		return activo;
	}
	public String getEspecialidad() {
		return especialidad;
	}	
}
