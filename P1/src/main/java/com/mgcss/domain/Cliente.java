package com.mgcss.domain;

import jakarta.persistence.*;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String email;
	private boolean bloqueado;
	private boolean verificado;
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;

	public enum TipoCliente {
		STANDARD, PREMIUM;
	}

	public Cliente() {}
	
	public Cliente(Long id, String nombre, String email, TipoCliente tipoCliente) {

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

        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public boolean tienePrioridad() {
		return this.tipoCliente == TipoCliente.PREMIUM;
	}

	public void actualizarDatos(String nom, String correo) {
		if (nom != null && !nom.isBlank())
			this.nombre = nom;
		if (correo != null && !correo.isBlank())
			this.email = correo;
	}

	public void ascenderCliente() {
        if (this.tipoCliente == TipoCliente.PREMIUM) {
            throw new IllegalStateException("El cliente ya es PREMIUM");
        }

        if (email.endsWith(".org") || email.endsWith(".edu")) {
            this.tipoCliente = TipoCliente.PREMIUM;
        }
    }

	public void bloquearCuenta() {
	    if (this.tipoCliente == TipoCliente.PREMIUM) {
	        throw new IllegalStateException("No se puede bloquear automáticamente a un cliente PREMIUM. Contacte con administración.");
	    }
	    this.bloqueado = true;
	}

	public void desbloquearCuenta() {
	    if (!this.bloqueado) {
	        throw new IllegalStateException("La cuenta ya está activa.");
	    }
	    if (this.nombre == null || this.nombre.isBlank()) {
	        throw new IllegalArgumentException("Nombre inválido");
	    }
	    this.bloqueado = false;
	}
	
	public void verificarIdentidad() {
        if (this.verificado) {
            throw new IllegalStateException("Ya verificado");
        }

        if (this.nombre == null || this.nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }

        if (this.email == null || !this.email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        this.verificado = true;
    }
}
