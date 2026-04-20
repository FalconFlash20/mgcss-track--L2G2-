package com.mgcss.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mgcss.domain.Cliente;

@Repository
public interface JpaClienteRepository extends JpaRepository<Cliente, Long> {
}