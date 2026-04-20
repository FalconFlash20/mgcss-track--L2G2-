package com.mgcss.service;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;

public class SolicitudService {
	private final SolicitudRepository solicitudRepository;
	private final TecnicoRepository tecnicoRepository;
    
    public SolicitudService(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository) {
    	this.solicitudRepository = solicitudRepository;
    	this.tecnicoRepository = tecnicoRepository;
    }
    
    public void asignarTecnico(Long solicitudId, Long tecnicoId) {

        Solicitud solicitud = solicitudRepository.findById(solicitudId).orElseThrow(()-> 
        new IllegalArgumentException("Solicitud no encontrada con ese ID"));

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new IllegalArgumentException("Técnico no encontrado"));

        solicitud.asignarTecnico(tecnico);

        solicitudRepository.save(solicitud);
    }
}
