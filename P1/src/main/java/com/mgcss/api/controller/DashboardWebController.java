package com.mgcss.api.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mgcss.service.ClienteService;
import com.mgcss.service.SolicitudService;
import com.mgcss.service.TecnicoService;

@Controller
public class DashboardWebController {

    private final SolicitudService solicitudService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    public DashboardWebController(
            SolicitudService solicitudService,
            ClienteService clienteService,
            TecnicoService tecnicoService) {

        this.solicitudService = solicitudService;
        this.clienteService = clienteService;
        this.tecnicoService = tecnicoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	
    	model.addAllAttributes(solicitudService.obtenerMetricas());
        model.addAllAttributes(clienteService.obtenerMetricas());
        model.addAllAttributes(tecnicoService.obtenerMetricas());

        return "dashboard";
    }
}