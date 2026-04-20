package com.mgcss.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mgcss.domain.Tecnico;

@Repository
public interface JpaTecnicoRepository extends JpaRepository<Tecnico, Long> {
}