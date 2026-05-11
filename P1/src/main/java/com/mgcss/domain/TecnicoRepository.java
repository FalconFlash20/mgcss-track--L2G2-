package com.mgcss.domain;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository {
	Tecnico save(Tecnico tecnico);
	Optional<Tecnico> findById(Long id);
	List<Tecnico> findAll();
}

