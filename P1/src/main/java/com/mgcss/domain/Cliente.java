package com.mgcss.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String email;
	private boolean bloqueado;
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;

	public enum TipoCliente {
		STANDARD, PREMIUM;
	}

	public Cliente(Long id, String nombre, String email, TipoCliente tipoCliente) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.tipoCliente = tipoCliente;
		/*
		 * if (id != null && id < 0) { throw new
		 * IllegalArgumentException("ID inválido"); }
		 * 
		 * if (nombre == null || nombre.isBlank()) { throw new
		 * IllegalArgumentException("Nombre obligatorio"); }
		 * 
		 * if (email == null || email.isBlank()) { throw new
		 * IllegalArgumentException("Email obligatorio"); }
		 * 
		 * if (tipoCliente == null) { throw new
		 * IllegalArgumentException("Tipo cliente obligatorio"); }
		 */
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

	public boolean TienePrioridad() {
		return this.tipoCliente == TipoCliente.PREMIUM;
	}

	public void actualizarDatos(String nom, String correo) {
		if (nom != null && !nom.isBlank())
			this.nombre = nom;
		if (correo != null && !correo.isBlank())
			this.email = correo;
	}

	// RN: Los clientes estándar pueden ascender a Premium si su email es
	// corporativo (.edu o .org)
	public void ascenderCliente() {
		if (this.tipoCliente == TipoCliente.STANDARD && (email.endsWith(".org") || email.endsWith(".edu"))) {
			this.tipoCliente = TipoCliente.PREMIUM;
		} else if (this.tipoCliente == TipoCliente.PREMIUM) {
			throw new IllegalStateException("El cliente ya no puede acceder más ");
		}
	}

	// RN: Solo se puede bloquear a un cliente si NO es PREMIUM (los VIP tienen soporte especial).
	// Si se intenta bloquear a un PREMIUM, lanza excepción.
	public void bloquearCuenta() {
	    if (this.tipoCliente == TipoCliente.PREMIUM) {
	        throw new IllegalStateException("No se puede bloquear automáticamente a un cliente PREMIUM. Contacte con administración.");
	    }
	    this.bloqueado = true;
	}

	// RN: Para desbloquear, el nombre no puede estar vacío y el cliente debe estar bloqueado previamente.
	public void desbloquearCuenta() {
	    if (!this.bloqueado) {
	        throw new IllegalStateException("La cuenta ya está activa.");
	    }
	    if (this.nombre == null || this.nombre.isBlank()) {
	        throw new IllegalArgumentException("No se puede desbloquear una cuenta sin nombre titular.");
	    }
	    this.bloqueado = false;
	}
}
