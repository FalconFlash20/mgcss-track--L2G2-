package com.mgcss.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;

@Service
public class SolicitudService {
	private final SolicitudRepository solicitudRepository;
	private final TecnicoRepository tecnicoRepository;
	private final ClienteRepository clienteRepository;
    

    
    public SolicitudService(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository,
			ClienteRepository clienteRepository) {
		this.solicitudRepository = solicitudRepository;
		this.tecnicoRepository = tecnicoRepository;
		this.clienteRepository = clienteRepository;
	}
    
	public void asignarTecnico(Long solicitudId, Long tecnicoId) {

        Solicitud solicitud = solicitudRepository.findById(solicitudId).orElseThrow(()-> 
        new IllegalArgumentException("Solicitud no encontrada con ese ID"));

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new IllegalArgumentException("Técnico no encontrado"));

        solicitud.asignarTecnico(tecnico);

        solicitudRepository.save(solicitud);
    }
    public Solicitud crearSolicitud(String descripcion, Long cId) {
    	Cliente c= clienteRepository.findById(cId).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
    	Solicitud s=new Solicitud(descripcion, EstadoSolicitud.ABIERTA,LocalDateTime.now(),c);
    	return solicitudRepository.save(s);
    }
    public Solicitud consultarSolicitud(Long sId) {
    	return solicitudRepository.findById(sId).orElseThrow(() -> new IllegalArgumentException("No se ha podido consultar la solicitud"));
    }
    public void cambiarEstado(Long sId, EstadoSolicitud estado) {
    	Solicitud s=consultarSolicitud(sId);
    	if(estado==EstadoSolicitud.CERRADA) {
    		s.cerrar();
    	} else if(estado==EstadoSolicitud.EN_PROCESO) {
    		s.iniciarProceso();
    	}
    	solicitudRepository.save(s);
    }
    public void reabrirSolicitud(Long sId) {
    	Solicitud s=consultarSolicitud(sId);
    	s.reabrir();
    	solicitudRepository.save(s);
    }
    public List<Solicitud> listar() {
    	return solicitudRepository.findAll();
    }
}
