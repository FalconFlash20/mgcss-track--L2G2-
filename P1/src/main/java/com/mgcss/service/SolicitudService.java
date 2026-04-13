package com.mgcss.service;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import com.sun.xml.txw2.IllegalAnnotationException;

public class SolicitudService {
	private final SolicitudRepository solicitudRepository;
    
    public SolicitudService(SolicitudRepository solicitudRepository) {
    	this.solicitudRepository = solicitudRepository;
    }
    
    public void asignarTecnico(Long solicitudId, Tecnico tecnico) {

        Solicitud solicitud = solicitudRepository.findById(solicitudId).orElseThrow(()-> 
        new IllegalArgumentException("Solicitud no encontrada con ese ID"));

        solicitud.asignarTecnico(tecnico);

        solicitudRepository.save(solicitud);
    }
}
