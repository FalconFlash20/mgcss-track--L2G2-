package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos que representa la información de salida de un miembro del equipo técnico")
public class TecnicoResponseDTO {
	
	@Schema(description = "Identificador único del técnico generado por el sistema", example = "4")
	private Long id;
	@Schema(description = "Nombre completo del técnico", example = "Carlos Gómez")
	private String nombre;
	@Schema(description = "Estado de disponibilidad del técnico para recibir nuevas incidencias", example = "true")
	private boolean activo;
	@Schema(description = "Especialización técnica actual asignada al operario", example = "HARDWARE")
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
