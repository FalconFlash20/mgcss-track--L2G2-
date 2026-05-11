package com.mgcss.api.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.DTO.ClienteRequestDTO;
import com.mgcss.api.DTO.ClienteResponseDTO;
import com.mgcss.domain.Cliente;
import com.mgcss.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class CLienteController {
	private final ClienteService clienteservice;

	public CLienteController(ClienteService clienteservice) {
		this.clienteservice = clienteservice;
	}
	
	@PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@RequestBody @Validated ClienteRequestDTO dto) {
        Cliente.TipoCliente tipo = Cliente.TipoCliente.valueOf(dto.TipoCLiente().toUpperCase());
        Cliente nuevo = new Cliente(null, dto.Nombre(), dto.Email(), tipo);
        Cliente guardado = clienteservice.crearCliente(nuevo);
        return ResponseEntity.ok(mapear(guardado));
    }
	@GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtener(@PathVariable Long id) {
        Cliente c = clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }

    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return clienteservice.listar().stream()
                .map(this::mapear)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}/ascender")
    public ResponseEntity<Void> ascender(@PathVariable Long id) {
        clienteservice.ascender(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/bloquear")
    public ResponseEntity<Void> bloquear(@PathVariable Long id) {
        clienteservice.bloquear(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/bloquear")
    public ResponseEntity<Void> desbloquear(@PathVariable Long id) {
        clienteservice.desbloquear(id);
        return ResponseEntity.noContent().build();
    }

    private ClienteResponseDTO mapear(Cliente c) {
        return new ClienteResponseDTO(
            c.getId(),
            c.getNombre(),
            c.getEmail(),
            c.isBloqueado(),
            c.isVerificado(),
            c.getTipoCliente().toString()
        );
    }
}
