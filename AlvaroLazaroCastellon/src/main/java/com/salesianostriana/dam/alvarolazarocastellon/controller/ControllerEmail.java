package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceEmail;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceModelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerEmail {

    @Autowired
    private ServiceEmail serviceEmail;

    @Autowired
    private ServiceJuego serviceJuego;

    @Autowired
    private ServiceModelo serviceModelo;

    @PostMapping("/")
    public String sendNews() {
        serviceEmail.sendEmail(serviceEmail.generateEmail(serviceJuego.findNewsForEmail(), serviceModelo.findNewConsolesForEmail()));
        return "redirect:/mostrarjuego";
    }

}
