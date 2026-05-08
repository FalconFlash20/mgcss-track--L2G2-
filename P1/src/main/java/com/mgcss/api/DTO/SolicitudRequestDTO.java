package com.mgcss.api.DTO;

public class SolicitudRequestDTO {

    private final String descripcion;
    private final Long clienteId;
    

    public SolicitudRequestDTO(String descripcion,Long clienteId) {
        this.descripcion = descripcion;
        this.clienteId = clienteId;
    }

    public String descripcion() {
        return descripcion;
    }

    public Long clienteId() {
        return clienteId;
    }
}