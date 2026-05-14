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

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {
	private final SolicitudService solicitudservice;

	public SolicitudController(SolicitudService solicitudservice) {
		this.solicitudservice = solicitudservice;
	}

	@PostMapping
	public ResponseEntity<SolicitudResponseDTO> crearSolicitud(@RequestBody @Validated SolicitudRequestDTO dto){
		Solicitud s=solicitudservice.crearSolicitud(dto.getDescripcion(), dto.getClienteId());
		return ResponseEntity.ok(mapear(s));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable("id") Long id){
		Solicitud s=solicitudservice.consultarSolicitud(id);
		return ResponseEntity.ok(mapear(s));
	}

	@GetMapping
	public List<SolicitudResponseDTO> listar(){
		return solicitudservice.listar().stream().map(this::mapear).toList();
	}
	
	@PatchMapping("/{id}/reabrir")
	public ResponseEntity<SolicitudResponseDTO> reabrirSolicitud(@PathVariable("id") Long id) {
	    solicitudservice.reabrirSolicitud(id);
	    Solicitud s = solicitudservice.consultarSolicitud(id);
	    return ResponseEntity.ok(mapear(s));
	}
	@PutMapping("/{id}/asignarTecnico")
	public ResponseEntity<SolicitudResponseDTO> asignarTecnico(@PathVariable("id") Long id,@RequestParam("tecnicoId") Long tecnicoId) {
	    solicitudservice.asignarTecnico(id, tecnicoId);
	    Solicitud s = solicitudservice.consultarSolicitud(id);
	    return ResponseEntity.ok(mapear(s));
	}

	@PutMapping("/{id}/cambiarEstado")
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
