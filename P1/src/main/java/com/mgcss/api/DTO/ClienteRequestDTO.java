package com.mgcss.api.DTO;

public class ClienteRequestDTO {
	private String nombre;
	private String email;
	private String tipoCLiente;
	public ClienteRequestDTO(String nombre, String email, String tipoCLiente) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.tipoCLiente = tipoCLiente;
	}
	public String Nombre() {
		return nombre;
	}
	public String Email() {
		return email;
	}
	public String TipoCLiente() {
		return tipoCLiente;
	}
	
	
	
}
