package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Modelo de datos requerido para registrar una nueva solicitud de asistencia técnica")
public class SolicitudRequestDTO {
	
	@Schema(description = "Detalle descriptivo de la incidencia o reparación solicitada", example = "El equipo no enciende tras una caída de tensión en la oficina")
	@NotBlank
	private String descripcion;
	@Schema(description = "Identificador único del cliente que abre la solicitud", example = "1")
	@NotNull
	private Long clienteId;
    
    public SolicitudRequestDTO() {
    	
    }
    
    public SolicitudRequestDTO(String descripcion,Long clienteId) {
        this.descripcion = descripcion;
        this.clienteId = clienteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getClienteId() {
        return clienteId;
    }
}