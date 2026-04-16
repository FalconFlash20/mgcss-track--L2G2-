package com.mgcss.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.mgcss.domain.Solicitud; 
@Repository public interface JpaSolicitudRepository extends JpaRepository<Solicitud, Long> 
{ 
}
