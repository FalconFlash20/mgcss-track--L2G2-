package com.mgcss.api.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.DTO.SolicitudRequestDTO;
import com.mgcss.api.DTO.SolicitudResponseDTO;
import com.mgcss.domain.Solicitud;
import com.mgcss.service.ClienteService;
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
		Solicitud s=solicitudservice.crearSolicitud(dto.descripcion(), dto.clienteId());
		return ResponseEntity.ok(mapear(s));
	}
	
	@GetMapping("/{id")
	public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable Long id){
		Solicitud s=solicitudservice.consultarSolicitud(id);
		return ResponseEntity.ok(mapear(s));
	}

	@GetMapping
	public List<SolicitudResponseDTO> listar(){
		return solicitudservice.listar().stream().map(this::mapear).collect(Collectors.toList());
	}
	
	private SolicitudResponseDTO mapear(Solicitud s) {
		return new SolicitudResponseDTO(s.getId(), s.getDescripcion(), s.getEstado().toString(), s.getFechaCierre(), null, s.isUrgente(), s.getTecnico(), s.getCliente());
		
	}
	
}
