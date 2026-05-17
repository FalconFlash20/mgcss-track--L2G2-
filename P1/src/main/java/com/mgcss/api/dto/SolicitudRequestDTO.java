package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos requerido para registrar una nueva solicitud de asistencia técnica")
public class SolicitudRequestDTO {
	
	@Schema(description = "Detalle descriptivo de la incidencia o reparación solicitada", example = "El equipo no enciende tras una caída de tensión en la oficina")
    private String descripcion;
	@Schema(description = "Identificador único del cliente que abre la solicitud", example = "1")
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