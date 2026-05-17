package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos que representa la información de salida de un cliente registrado")
public class ClienteResponseDTO {
	
	@Schema(description = "Identificador único del cliente asignado por la base de datos", example = "1")
	private Long id;
	@Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
	private String nombre;
	@Schema(description = "Dirección de correo electrónico del cliente", example = "juan.perez@gmail.com")
	private String email;
	@Schema(description = "Indica si el cliente tiene el acceso restringido o bloqueado", example = "false")
	private boolean bloqueado;
	@Schema(description = "Indica si la cuenta del cliente ha sido verificada correctamente", example = "true")
	private boolean verificado;
	@Schema(description = "Categoría o nivel actual del cliente dentro del sistema", example = "STANDARD")
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
