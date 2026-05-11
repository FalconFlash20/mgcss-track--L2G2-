package com.mgcss.domain;

import java.util.*;

import org.springframework.data.repository.Repository;


public interface ClienteRepository extends Repository<Cliente,Long>{
  /*  Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();*/
	<S extends Cliente> S save(S entity);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
}
