package com.mgcss.api.dto;

public class ClienteResponseDTO {
	private Long id;
	private String nombre;
	private String email;
	private boolean bloqueado;
	private boolean verificado;
	private String tipoCliente;
	public ClienteResponseDTO(Long id, String nombre, String email, boolean bloqueado, boolean verificado,
			String tipoCliente) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.bloqueado = bloqueado;
		this.verificado = verificado;
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
	public boolean getBloqueado() {
		return bloqueado;
	}
	public boolean getVerificado() {
		return verificado;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
}
