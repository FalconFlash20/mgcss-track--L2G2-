package com.mgcss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;

@Service
public class TecnicoService {
	private final TecnicoRepository tecnicoRepository;

	public TecnicoService(TecnicoRepository tecnicoRepository) {
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
	
}
