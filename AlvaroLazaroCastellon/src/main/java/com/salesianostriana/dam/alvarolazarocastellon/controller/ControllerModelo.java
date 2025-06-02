package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceModelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerModelo {

    @Autowired
    private ServiceModelo serviceModelo;

    @Autowired
    private ServiceConsola serviceConsola;

    @GetMapping("/mostrarmodelos/añadirmodelo")
    public String showGames(Model model) {
        Modelo modelo = new Modelo();
        model.addAttribute("modelo", modelo);
        model.addAttribute("consolas", serviceConsola.findAll());
        return "addmodel";
    }

    @PostMapping("/mostrarmodelos")
    public String addGame(@ModelAttribute("modelo") Modelo modelo) {
        if (modelo.getConsola() != null) {
            Consola consola = serviceConsola.getById(modelo.getConsola().getId());
            modelo.setConsola(consola);
        }
        serviceModelo.save(modelo);
        return "redirect:/mostrarmodelos";
    }

    @GetMapping("/mostrarmodelos/editar/{id}")
    public String showEditGame(Model model, @PathVariable Long id) {
        model.addAttribute("modelo", serviceModelo.getById(id));
        model.addAttribute("consolas", serviceConsola.findAll());
        return "addmodel";
    }

    @PostMapping("/mostrarmodelos/editar/submit")
    public String editGame(@ModelAttribute("modelo") Modelo j) {

        serviceModelo.edit(j);

        return "redirect:/mostrarmodelos";
    }

    @GetMapping("/mostrarmodelos")
    public String showGame(Model model, @ModelAttribute("palabraClave") String palabraClave) {
        model.addAttribute("modelo", serviceModelo.findAll());
        model.addAttribute("palabraClave", palabraClave);
        return "showmodels";
    }

}
