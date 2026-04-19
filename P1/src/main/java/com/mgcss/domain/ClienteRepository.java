package com.mgcss.domain;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente save(Cliente cliente);
	Optional<Cliente> findById(Long id);
	List<Cliente> findAll();
}
