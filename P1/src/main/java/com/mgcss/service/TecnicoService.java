package com.mgcss.service;

import java.util.List;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;

public class TecnicoService {
	private final TecnicoRepository tecnicoRepository;

	public TecnicoService(TecnicoRepository tecnicoRepository) {
		this.tecnicoRepository = tecnicoRepository;
	}
	public void crearTecnico(Tecnico t) {
		tecnicoRepository.save(t);
	}
	public Tecnico consultarTecnico(Long TecnicoId) {
    	return tecnicoRepository.findById(TecnicoId).orElseThrow(() -> new IllegalArgumentException("No se ha podido consultar el tecnico"));		
	}
	public List<Tecnico> listarTecnicos() {
		return tecnicoRepository.findAll();
	}
	public void activar(Long TecnicoId) {
		Tecnico t=consultarTecnico(TecnicoId);
		t.activar();
		tecnicoRepository.save(t);
	}
	public void desactivar(Long TecnicoId) {
		Tecnico t=consultarTecnico(TecnicoId);
		t.desactivar();
		tecnicoRepository.save(t);
	}
	
}
