package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos requerido para registrar un nuevo cliente en el sistema")
public class ClienteRequestDTO {
	
	@Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
	private String nombre;
	@Schema(description = "Dirección de correo electrónico única del cliente", example = "juan.perez@gmail.com")
	private String email;
	@Schema(description = "Tipo o categoría inicial del cliente (PREMIUM,STANDARD)", example = "PREMIUM")
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
