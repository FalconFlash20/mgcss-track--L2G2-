package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Modelo de datos requerido para registrar un nuevo cliente en el sistema")
public class ClienteRequestDTO {
	
	@Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
	@NotBlank
	private String nombre;
	@Schema(description = "Dirección de correo electrónico única del cliente", example = "juan.perez@gmail.com")
	@NotBlank
	@Email
	private String email;
	@Schema(description = "Tipo o categoría inicial del cliente (PREMIUM,STANDARD)", example = "PREMIUM")
	@NotBlank
	private String tipoCliente;
	
	public ClienteRequestDTO(){
		
	}
	
	public ClienteRequestDTO(String nombre, String email, String tipoCliente) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.tipoCliente = tipoCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEmail() {
		return email;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	
}
