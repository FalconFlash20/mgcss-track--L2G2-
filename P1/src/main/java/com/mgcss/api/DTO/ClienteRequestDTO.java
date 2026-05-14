package com.mgcss.api.DTO;

public class ClienteRequestDTO {
	private String nombre;
	private String email;
	private String tipoCLiente;
	
	public ClienteRequestDTO(){
		
	}
	
	public ClienteRequestDTO(String nombre, String email, String tipoCLiente) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.tipoCLiente = tipoCLiente;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEmail() {
		return email;
	}
	public String getTipoCLiente() {
		return tipoCLiente;
	}
	
	
	
}
