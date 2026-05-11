package com.mgcss.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface TecnicoRepository extends CrudRepository<Tecnico,Long>{
	/*Tecnico save(Tecnico tecnico);
	Optional<Tecnico> findById(Long id);
	List<Tecnico> findAll();*/
	<S extends Tecnico> S save(S entity);
    Optional<Tecnico> findById(Long id);
    List<Tecnico> findAll();
}

