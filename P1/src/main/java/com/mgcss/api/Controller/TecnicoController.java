package com.mgcss.api.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.DTO.TecnicoRequestDTO;
import com.mgcss.api.DTO.TecnicoResponseDTO;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {
	private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public ResponseEntity<TecnicoResponseDTO> crear(@RequestBody @Validated TecnicoRequestDTO dto) {
        Tecnico.Especialidad esp = Tecnico.Especialidad.valueOf(dto.Especialidad().toUpperCase());
        Tecnico t = new Tecnico(dto.Nombre(), true, esp);
        Tecnico guardado = tecnicoService.crearTecnico(t);
        return ResponseEntity.ok(mapear(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoResponseDTO> consultar(@PathVariable Long id) {
        Tecnico t = tecnicoService.consultarTecnico(id);
        return ResponseEntity.ok(mapear(t));
    }

    @GetMapping
    public List<TecnicoResponseDTO> listar() {
        return tecnicoService.listarTecnicos().stream()
                .map(this::mapear)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        tecnicoService.activar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        tecnicoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/especialidad")
    public ResponseEntity<Void> actualizarEspecialidad(
            @PathVariable Long id, 
            @RequestParam String nuevaesp) {
        
        Tecnico.Especialidad esp = Tecnico.Especialidad.valueOf(nuevaesp.toUpperCase());
        
        tecnicoService.actualizarEspecialidad(id, esp);
        return ResponseEntity.noContent().build();
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
