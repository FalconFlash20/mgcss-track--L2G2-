package com.mgcss.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos detallado devuelto tras consultar o procesar una solicitud de asistencia")
public class SolicitudResponseDTO {

	@Schema(description = "Identificador único de la solicitud generado automáticamente", example = "15")
	private final Long id;
	@Schema(description = "Texto descriptivo de la incidencia registrada", example = "Pantalla rota tras impacto")
	private final String descripcion;
	@Schema(description = "Estado operativo actual de la resolución de la incidencia", example = "EN_PROCESO")
	private final String estado;
	@Schema(description = "Fecha y hora exacta en la que se cerró la solicitud (nulo si sigue abierta)")
	private LocalDateTime fechaCierre;
	@Schema(description = "Tiempo total estimado o consumido para la ejecución del servicio (SLA)")
	private LocalDateTime tiempoej;
	@Schema(description = "Indica si la solicitud requiere atención prioritaria e inmediata", example = "true")
	private boolean urgente;
	@Schema(description = "Identificador del técnico asignado", example = "3")
	private final Long tecnicoId;
	@Schema(description = "Nombre del técnico asignado", example = "Carlos Martínez")
	private final String tecnicoNombre;
	@Schema(description = "Identificador del cliente asociado", example = "1")
	private final Long clienteId;
	@Schema(description = "Nombre del cliente asociado", example = "Alejandro Martín")
	private final String clienteNombre;

	private SolicitudResponseDTO(Builder b) {
		this.id = b.id;
		this.descripcion = b.descripcion;
		this.estado = b.estado;
		this.fechaCierre = b.fechaCierre;
		this.tiempoej = b.tiempoej;
		this.urgente = b.urgente;
		this.tecnicoId = b.tecnicoId;
		this.tecnicoNombre = b.tecnicoNombre;
		this.clienteId = b.clienteId;
		this.clienteNombre = b.clienteNombre;
	}

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public LocalDateTime getTiempoej() {
		return tiempoej;
	}

	public boolean getUrgente() {
		return urgente;
	}

	public Long getTecnicoId() {
		return tecnicoId;
	}

	public String getTecnicoNombre() {
		return tecnicoNombre;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public Long getId() {
		return id;
	}

	public String getEstado() {
		return estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static class Builder {
		private Long id;
		private String descripcion;
		private String estado;
		private LocalDateTime fechaCierre;
		private LocalDateTime tiempoej;
		private boolean urgente;
		private Long tecnicoId;
		private String tecnicoNombre;
		private Long clienteId;
		private String clienteNombre;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder descripcion(String descripcion) {
			this.descripcion = descripcion;
			return this;
		}

		public Builder estado(String estado) {
			this.estado = estado;
			return this;
		}

		public Builder fechaCierre(LocalDateTime fechaCierre) {
			this.fechaCierre = fechaCierre;
			return this;
		}

		public Builder tiempoej(LocalDateTime tiempoej) {
			this.tiempoej = tiempoej;
			return this;
		}

		public Builder urgente(boolean urgente) {
			this.urgente = urgente;
			return this;
		}

		public Builder tecnicoId(Long tecnicoId) {
			this.tecnicoId = tecnicoId;
			return this;
		}

		public Builder tecnicoNombre(String tecnicoNombre) {
			this.tecnicoNombre = tecnicoNombre;
			return this;
		}

		public Builder clienteId(Long clienteId) {
			this.clienteId = clienteId;
			return this;
		}

		public Builder clienteNombre(String clienteNombre) {
			this.clienteNombre = clienteNombre;
			return this;
		}

		public SolicitudResponseDTO build() {
			return new SolicitudResponseDTO(this);
		}
	}

}