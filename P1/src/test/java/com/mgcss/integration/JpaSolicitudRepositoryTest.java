package com.mgcss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.infrastructure.persistence.JpaSolicitudRepository;

@DataJpaTest
@Tag("integration")
class JpaSolicitudRepositoryTest {

    @Autowired
    private JpaSolicitudRepository repository;

    @Test
    void guardarSolicitud() {

        Solicitud s = new Solicitud(null, EstadoSolicitud.ABIERTA, LocalDateTime.now());

        Solicitud sBD = repository.save(s);

        var resultado = repository.findById(sBD.getId());

        assertTrue(resultado.isPresent());
        assertEquals(EstadoSolicitud.ABIERTA, resultado.get().getEstado());
    }
}