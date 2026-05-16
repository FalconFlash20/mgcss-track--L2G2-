package com.mgcss.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.dto.SolicitudRequestDTO;
import com.mgcss.api.dto.SolicitudResponseDTO;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.service.SolicitudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitud Controller", description = "Endpoints para la creación, asignación y gestión de estados de las solicitudes")
public class SolicitudController {
	private final SolicitudService solicitudservice;

	public SolicitudController(SolicitudService solicitudservice) {
		this.solicitudservice = solicitudservice;
	}

	@PostMapping
	@Operation(summary = "Crear una nueva solicitud", description = "Registra una solicitud de asistencia técnica asociada a un cliente específico.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Solicitud creada correctamente"),
		@ApiResponse(responseCode = "400", description = "Datos de entrada incorrectos"),
		@ApiResponse(responseCode = "404", description = "El ID del cliente proporcionado no existe")
	})
	public ResponseEntity<SolicitudResponseDTO> crearSolicitud(@RequestBody @Validated SolicitudRequestDTO dto){
		Solicitud s=solicitudservice.crearSolicitud(dto.getDescripcion(), dto.getClienteId());
		return ResponseEntity.ok(mapear(s));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Consultar solicitud por ID", description = "Recupera toda la información detallada de una solicitud a partir de su identificador único.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Solicitud localizada con éxito"),
		@ApiResponse(responseCode = "404", description = "No se ha encontrado ninguna solicitud con ese ID")
	})
	public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable("id") Long id){
		Solicitud s=solicitudservice.consultarSolicitud(id);
		return ResponseEntity.ok(mapear(s));
	}

	@GetMapping
	@Operation(summary = "Listar todas las solicitudes", description = "Obtiene un listado global con todas las solicitudes de asistencia registradas en el sistema.")
	@ApiResponse(responseCode = "200", description = "Listado de solicitudes recuperado correctamente")
	public List<SolicitudResponseDTO> listar(){
		return solicitudservice.listar().stream().map(this::mapear).toList();
	}
	
	@PatchMapping("/{id}/reabrir")
	@Operation(summary = "Reabrir una solicitud cerrada", description = "Permite reabrir una solicitud que ya había sido finalizada para volver a trabajar en ella.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Solicitud reabierta con éxito"),
		@ApiResponse(responseCode = "400", description = "No se puede reabrir la solicitud en su estado actual"),
		@ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
	})
	public ResponseEntity<SolicitudResponseDTO> reabrirSolicitud(@PathVariable("id") Long id) {
	    solicitudservice.reabrirSolicitud(id);
	    Solicitud s = solicitudservice.consultarSolicitud(id);
	    return ResponseEntity.ok(mapear(s));
	}
	
	@PutMapping("/{id}/asignarTecnico")
	@Operation(summary = "Asignar un técnico a la solicitud", description = "Vincula a un técnico del equipo para hacerse cargo de la resolución de la incidencia.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Técnico asignado correctamente"),
		@ApiResponse(responseCode = "404", description = "No se ha encontrado la solicitud o el técnico especificado")
	})
	public ResponseEntity<SolicitudResponseDTO> asignarTecnico(@PathVariable("id") Long id,@RequestParam("tecnicoId") Long tecnicoId) {
	    solicitudservice.asignarTecnico(id, tecnicoId);
	    Solicitud s = solicitudservice.consultarSolicitud(id);
	    return ResponseEntity.ok(mapear(s));
	}

	@PutMapping("/{id}/cambiarEstado")
	@Operation(summary = "Cambiar estado de la solicitud", description = "Modifica manualmente el estado actual de la solicitud (EJ: EN_PROCESO, RESUELTA, CERRADA).")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Estado actualizado con éxito"),
		@ApiResponse(responseCode = "400", description = "El estado proporcionado no es válido o la transición no está permitida"),
		@ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
	})
	public ResponseEntity<SolicitudResponseDTO> cambiarEstado(@PathVariable("id") Long id,@RequestParam("estado") String estado) {
	    solicitudservice.cambiarEstado(id, EstadoSolicitud.valueOf(estado));
	    Solicitud s = solicitudservice.consultarSolicitud(id);
	    return ResponseEntity.ok(mapear(s));
	}
	
	private SolicitudResponseDTO mapear(Solicitud s) {
		return new SolicitudResponseDTO.Builder()
	            .id(s.getId())
	            .descripcion(s.getDescripcion())
	            .estado(s.getEstado().toString())
	            .fechaCierre(s.getFechaCierre())
	            .tiempoej(s.getSLA())
	            .urgente(s.isUrgente())
	            .tecnico(s.getTecnico())
	            .cliente(s.getCliente())
	            .build();
	}
	
}
