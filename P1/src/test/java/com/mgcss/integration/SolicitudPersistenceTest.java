package com.mgcss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Cliente.TipoCliente;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Solicitud.EstadoSolicitud;
import com.mgcss.infrastructure.persistence.JpaSolicitudRepository;

@DataJpaTest
@Tag("integration")
class SolicitudPersistenceTest {

    @Autowired
    private JpaSolicitudRepository repository;

    private Cliente cliente() {
        return new Cliente(null, "Pepe", "pepe@test.com", TipoCliente.STANDARD);
    }

    @Test
    void deberiaPersistirHistorialEstados() {
        Solicitud s = new Solicitud("desc", EstadoSolicitud.ABIERTA, LocalDateTime.now(), cliente());

        s.iniciarProceso();
        s.cerrar();
        s.reabrir();

        Solicitud guardada = repository.save(s);

        Solicitud recuperada = repository.findById(guardada.getId()).orElseThrow();

        List<EstadoSolicitud> historial = recuperada.getHistorialEstados();
        System.out.println(historial);
        
        assertEquals(4, historial.size());
        assertEquals(EstadoSolicitud.ABIERTA, historial.get(0));
        assertEquals(EstadoSolicitud.EN_PROCESO, historial.get(1));
        assertEquals(EstadoSolicitud.CERRADA, historial.get(2));
        assertEquals(EstadoSolicitud.EN_PROCESO, historial.get(3));
    }
}
