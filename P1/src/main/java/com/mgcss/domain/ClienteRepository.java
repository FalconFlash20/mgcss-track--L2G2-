package com.mgcss.domain;

import java.util.*;

import org.springframework.data.repository.CrudRepository;


public interface ClienteRepository extends CrudRepository<Cliente,Long>{
  /*  Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();*/
	<S extends Cliente> S save(S entity);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
}
