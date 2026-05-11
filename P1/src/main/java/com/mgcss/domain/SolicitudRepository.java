package com.mgcss.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface SolicitudRepository extends Repository<Solicitud,Long>{
	/*Solicitud save(Solicitud solicitud);
	Optional<Solicitud> findById(Long id);
	List<Solicitud> findAll();*/
	<S extends Solicitud> S save(S entity); 
    Optional<Solicitud> findById(Long id);
    List<Solicitud> findAll();
}
