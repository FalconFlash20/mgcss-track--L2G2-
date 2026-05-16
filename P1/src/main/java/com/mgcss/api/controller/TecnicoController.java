package com.mgcss.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.dto.TecnicoRequestDTO;
import com.mgcss.api.dto.TecnicoResponseDTO;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/tecnicos")
@Tag(name = "Tecnico Controller", description = "Endpoints para el registro, gestión de estado operativo y especialidades del equipo técnico")
public class TecnicoController {
	private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo técnico", description = "Introduce un nuevo miembro en el equipo técnico asignándole una especialidad inicial (ej. HARDWARE, REDES).")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Técnico dado de alta correctamente"),
		@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o especialidad no reconocida")
	})
    public ResponseEntity<TecnicoResponseDTO> crear(@RequestBody @Validated TecnicoRequestDTO dto) {
        Tecnico.Especialidad esp = Tecnico.Especialidad.valueOf(dto.getEspecialidad().toUpperCase());
        Tecnico t = new Tecnico(dto.getNombre(), true, esp);
        Tecnico guardado = tecnicoService.crearTecnico(t);
        return ResponseEntity.ok(mapear(guardado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar técnico por ID", description = "Recupera la ficha técnica e información de disponibilidad de un operario mediante su ID.")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Técnico localizado con éxito"),
		@ApiResponse(responseCode = "404", description = "No existe ningún técnico con el identificador facilitado")
	})
    public ResponseEntity<TecnicoResponseDTO> consultar(@PathVariable("id") Long id) {
        Tecnico t = tecnicoService.consultarTecnico(id);
        return ResponseEntity.ok(mapear(t));
    }

    @GetMapping
    @Operation(summary = "Listar la plantilla de técnicos", description = "Obtiene una lista con todos los técnicos registrados, tanto activos como inactivos.")
    @ApiResponse(responseCode = "200", description = "Plantilla recuperada correctamente")
    public List<TecnicoResponseDTO> listar() {
        return tecnicoService.listarTecnicos().stream()
                .map(this::mapear)
                .toList();
    }

    @PutMapping("/{id}/activar")
    @Operation(summary = "Activar técnico", description = "Cambia el estado del operario a activo para que pueda recibir nuevas asignaciones de solicitudes.")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Técnico activado correctamente"),
		@ApiResponse(responseCode = "404", description = "Técnico no encontrado")
	})
    public ResponseEntity<TecnicoResponseDTO> activar(@PathVariable("id") Long id) {
        tecnicoService.activar(id);
        Tecnico t=tecnicoService.consultarTecnico(id);
        return ResponseEntity.ok(mapear(t));
    }

    @PutMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar técnico", description = "Pone al técnico en estado inactivo para pausar temporalmente la asignación de incidencias.")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Técnico desactivado de forma correcta"),
		@ApiResponse(responseCode = "404", description = "Técnico no encontrado")
	})
    public ResponseEntity<TecnicoResponseDTO> desactivar(@PathVariable("id") Long id) {
        tecnicoService.desactivar(id);
        Tecnico t=tecnicoService.consultarTecnico(id);
        return ResponseEntity.ok(mapear(t));
    }
    
    @PutMapping("/{id}/especialidad")
    @Operation(summary = "Actualizar especialidad del técnico", description = "Modifica la rama de especialización de un técnico existente en el sistema.")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Especialidad modificada con éxito"),
		@ApiResponse(responseCode = "400", description = "El nombre de la nueva especialidad no pertenece a los tipos válidos"),
		@ApiResponse(responseCode = "404", description = "Técnico no encontrado")
	})
    public ResponseEntity<TecnicoResponseDTO> actualizarEspecialidad(@PathVariable("id") Long id, @RequestParam("nuevaesp") String nuevaesp) {       
        Tecnico.Especialidad esp = Tecnico.Especialidad.valueOf(nuevaesp.toUpperCase());       
        tecnicoService.actualizarEspecialidad(id, esp);
        Tecnico t=tecnicoService.consultarTecnico(id);
        return ResponseEntity.ok(mapear(t));
        }
    
    private TecnicoResponseDTO mapear(Tecnico t) {
        return new TecnicoResponseDTO(
            t.getId(),
            t.getNombre(),
            t.isActivo(),
            t.getEspecialidad().toString()
        );
    }
}
