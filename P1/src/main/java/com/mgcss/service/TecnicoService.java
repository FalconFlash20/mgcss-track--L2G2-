package com.mgcss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mgcss.domain.Tecnico;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;

@Service
public class TecnicoService {
	private final JpaTecnicoRepository tecnicoRepository;

	public TecnicoService(JpaTecnicoRepository tecnicoRepository) {
		this.tecnicoRepository = tecnicoRepository;
	}
	public Tecnico crearTecnico(Tecnico t) {
		return tecnicoRepository.save(t);
	}
	public Tecnico consultarTecnico(Long tId) {
    	return tecnicoRepository.findById(tId).orElseThrow(() -> new IllegalArgumentException("No se ha podido consultar el tecnico"));		
	}
	public List<Tecnico> listarTecnicos() {
		return tecnicoRepository.findAll();
	}
	public void activar(Long tId) {
		Tecnico t=consultarTecnico(tId);
		t.activar();
		tecnicoRepository.save(t);
	}
	public void desactivar(Long tId) {
		Tecnico t=consultarTecnico(tId);
		t.desactivar();
		tecnicoRepository.save(t);
	}
	public void actualizarEspecialidad(Long id, Tecnico.Especialidad nueva) {
        Tecnico t = consultarTecnico(id);
        t.actualizarEspecialidad(nueva);
        tecnicoRepository.save(t);
    }
	
	public Map<String, Object> obtenerMetricas() {
	    List<Tecnico> tecnicos = tecnicoRepository.findAll();
	    Map<String, Object> metricas = new HashMap<>();

	    metricas.put("totalTecnicos", tecnicos.size());
	    metricas.put("activos", tecnicos.stream().filter(Tecnico::isActivo).count());
	    metricas.put("inactivos", tecnicos.stream().filter(t -> !t.isActivo()).count());
	    metricas.put("hardware", tecnicos.stream().filter(t -> t.getEspecialidad() == Tecnico.Especialidad.HARDWARE).count());
	    metricas.put("software", tecnicos.stream().filter(t -> t.getEspecialidad() == Tecnico.Especialidad.SOFTWARE).count());

	    return metricas;
	}
	
}
