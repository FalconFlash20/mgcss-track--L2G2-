package com.mgcss.api.DTO;

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
	public Long Id() {
		return id;
	}
	public String Nombre() {
		return nombre;
	}
	public String Email() {
		return email;
	}
	public boolean Bloqueado() {
		return bloqueado;
	}
	public boolean Verificado() {
		return verificado;
	}
	public String TipoCliente() {
		return tipoCliente;
	}
}
