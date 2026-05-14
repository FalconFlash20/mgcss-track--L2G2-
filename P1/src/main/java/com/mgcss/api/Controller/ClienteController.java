package com.mgcss.api.Controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.DTO.ClienteRequestDTO;
import com.mgcss.api.DTO.ClienteResponseDTO;
import com.mgcss.domain.Cliente;
import com.mgcss.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	private final ClienteService clienteservice;

	public ClienteController(ClienteService clienteservice) {
		this.clienteservice = clienteservice;
	}
	
	@PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@RequestBody @Validated ClienteRequestDTO dto) {
        Cliente.TipoCliente tipo = Cliente.TipoCliente.valueOf(dto.getTipoCLiente().toUpperCase());
        Cliente nuevo = new Cliente(null, dto.getNombre(), dto.getEmail(), tipo);
        Cliente guardado = clienteservice.crearCliente(nuevo);
        return ResponseEntity.ok(mapear(guardado));
    }
	@GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> consultar(@PathVariable("id") Long id) {
        Cliente c = clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }

    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return clienteservice.listar().stream()
                .map(this::mapear)
                .toList();
    }

    @PutMapping("/{id}/ascender")
    public ResponseEntity<ClienteResponseDTO> ascender(@PathVariable("id") Long id) {
        clienteservice.ascender(id);
        Cliente c=clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }

    @PutMapping("/{id}/bloquear")
    public ResponseEntity<ClienteResponseDTO> bloquear(@PathVariable("id") Long id) {
        clienteservice.bloquear(id);
        Cliente c=clienteservice.consultarCliente(id);
        return ResponseEntity.ok(mapear(c));
    }
    @PutMapping("/{id}/desbloquear")
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
