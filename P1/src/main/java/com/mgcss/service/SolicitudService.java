package com.mgcss.service;

import java.util.List;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
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
    public void crearSolicitud(Solicitud s) {
    	solicitudRepository.save(s);
    }
    public Solicitud consultarSolicitud(Long SolicitudId) {
    	return solicitudRepository.findById(SolicitudId).orElseThrow(() -> new IllegalArgumentException("No se ha podido consultar la solicitud"));
    }
    public void cambiar(Long SolicitudId, EstadoSolicitud estado) {
    	Solicitud s=consultarSolicitud(SolicitudId);
    	if(estado==EstadoSolicitud.CERRADA) {
    		s.cerrar();
    	} else if(estado==EstadoSolicitud.EN_PROCESO) {
    		s.iniciarProceso();
    	}
    	solicitudRepository.save(s);
    }
    public void reabrirSolicitud(Long SolicitudId) {
    	Solicitud s=consultarSolicitud(SolicitudId);
    	s.reabrir();
    	solicitudRepository.save(s);
    }
    public List<Solicitud> listar() {
    	return solicitudRepository.findAll();
    }
}
