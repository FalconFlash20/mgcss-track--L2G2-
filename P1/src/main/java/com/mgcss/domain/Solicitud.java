package com.mgcss.domain;

import java.time.LocalDateTime;

public class Solicitud {

    private Long id;
    private String estado
    private LocalDateTime fechaCreacion;

    public Solicitud() {
    }

    public Solicitud(Long id, String estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}