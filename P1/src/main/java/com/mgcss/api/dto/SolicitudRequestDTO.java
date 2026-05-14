package com.mgcss.api.dto;

public class SolicitudRequestDTO {

    private String descripcion;
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