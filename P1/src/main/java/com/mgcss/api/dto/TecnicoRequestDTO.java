package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos requerido para dar de alta a un nuevo técnico en el equipo")
public class TecnicoRequestDTO {
	
	@Schema(description = "Nombre completo del técnico o especialista", example = "Carlos Gómez")
	private String nombre;
	@Schema(description = "Especialidad técnica del operario (ej. Hardware, Redes, Software)", example = "Hardware")
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
