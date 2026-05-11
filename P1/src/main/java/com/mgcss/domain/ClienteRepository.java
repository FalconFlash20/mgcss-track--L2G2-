package com.mgcss.domain;

import java.util.*;


public interface ClienteRepository {
  /*  Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();*/
	<S extends Cliente> S save(S entity);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
}
