package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerConsola {

    @Autowired
    private ServiceConsola serviceConsola;
/*
    @GetMapping("/mostrarconsolas")
    public String mostrarConsolas(Model model) {
        model.addAttribute("consola", serviceConsola.findAll());
        return "mostrarconsolas";
    }*/
}
