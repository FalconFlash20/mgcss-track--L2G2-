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
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.dto.ClienteRequestDTO;
import com.mgcss.api.dto.ClienteResponseDTO;
import com.mgcss.domain.Cliente;
import com.mgcss.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Cliente Controller", description = "Endpoints para la gestión de los clientes")
public class ClienteController {
	private final ClienteService clienteservice;

	public ClienteController(ClienteService clienteservice) {
		this.clienteservice = clienteservice;
	}
	
	@PostMapping
	@Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema a partir de su nombre, email y tipo inicial.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Cliente registrado con éxito"),
		@ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o formato de tipo de cliente incorrecto")
	})
    public ResponseEntity<ClienteResponseDTO> crear(@RequestBody @Validated ClienteRequestDTO dto) {
        Cliente.TipoCliente tipo = Cliente.TipoCliente.valueOf(dto.getTipoCLiente().toUpperCase());
        Cliente nuevo = new Cliente(null, dto.getNombre(), dto.getEmail(), tipo);
        Cliente guardado = clienteservice.crearCliente(nuevo);
        return ResponseEntity.ok(mapear(guardado));
    }
	@GetMapping("/{id}")
	@Operation(summary = "Consultar cliente por ID", description = "Consulta un cliente específico utilizando su ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Cliente localizado correctamente"),
		@ApiResponse(responseCode = "404", description = "No se encontró ningún cliente con el ID especificado")
	})
    public ResponseEntity<ClienteResponseDTO> consultar(@PathVariable("id") Long id) {
        Cliente c = clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Recupera una lista completa con todos los clientes registrados en la plataforma.")
    @ApiResponse(responseCode = "200", description = "Listado de clientes recuperado con éxito")
    public List<ClienteResponseDTO> listar() {
        return clienteservice.listar().stream()
                .map(this::mapear)
                .toList();
    }

    @PutMapping("/{id}/ascender")
    @Operation(summary = "Ascender categoría de cliente", description = "Incrementa o promociona el nivel/categoría del cliente seleccionado.")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Cliente ascendido con éxito"),
		@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	})
    public ResponseEntity<ClienteResponseDTO> ascender(@PathVariable("id") Long id) {
        clienteservice.ascender(id);
        Cliente c=clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }

    @PutMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear acceso de cliente", description = "Restringe temporal o permanentemente el acceso de un cliente en el sistema cambiándolo a estado bloqueado.")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Cliente bloqueado correctamente"),
		@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	})
    public ResponseEntity<ClienteResponseDTO> bloquear(@PathVariable("id") Long id) {
        clienteservice.bloquear(id);
        Cliente c=clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }
    @PutMapping("/{id}/desbloquear")
    @Operation(summary = "Bloquear acceso de cliente", description = "Restringe temporal o permanentemente las acciones de un cliente en el sistema cambiándolo a estado bloqueado.")
    @ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Cliente bloqueado correctamente"),
		@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	})
    public ResponseEntity<ClienteResponseDTO> desbloquear(@PathVariable("id") Long id) {
        clienteservice.desbloquear(id);
        Cliente c=clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }

    private ClienteResponseDTO mapear(Cliente c) {
        return new ClienteResponseDTO(c.getId(), c.getNombre(), c.getEmail(),
            c.isBloqueado(), c.isVerificado(), c.getTipoCliente().toString());
    }
}
