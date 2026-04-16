package com.mgcss.domain;

public class Cliente {
private Long id;
private String nombre;
private String email;
private TipoCliente tipoCliente;

public enum TipoCliente{
	STANDARD, PREMIUM;
}

public Cliente(Long id, String nombre, String email, TipoCliente tipoCliente) {
	this.id = id;
	this.nombre = nombre;
	this.email = email;
	this.tipoCliente = tipoCliente;
    if (id != null && id < 0) {
        throw new IllegalArgumentException("ID inválido");
    }

    if (nombre == null || nombre.isBlank()) {
        throw new IllegalArgumentException("Nombre obligatorio");
    }

    if (email == null || email.isBlank()) {
        throw new IllegalArgumentException("Email obligatorio");
    }

    if (tipoCliente == null) {
        throw new IllegalArgumentException("Tipo cliente obligatorio");
    }
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public TipoCliente getTipoCliente() {
	return tipoCliente;
}

public void setTipoCliente(TipoCliente tipoCliente) {
	this.tipoCliente = tipoCliente;
}

}
