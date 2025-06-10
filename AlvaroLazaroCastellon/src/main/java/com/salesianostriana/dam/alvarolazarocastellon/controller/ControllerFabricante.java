package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Fabricante;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceFabricante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControllerFabricante {

    @Autowired
    private ServiceFabricante serviceFabricante;

    @GetMapping("/mostrarfabricantes/añadirfabricante")
    public String showGames(Model model) {
        model.addAttribute("fabricante", new Fabricante());
        return "addmanufacturer";
    }

    @PostMapping("/mostrarfabricantes")
    public String addGame(@ModelAttribute("fabricante") Fabricante fabricante) {
        serviceFabricante.save(fabricante);
        return "redirect:/mostrarfabricantes";
    }

    @GetMapping("/mostrarfabricantes")
    public String showManufacturer(Model model, @RequestParam(required = false) String name) {
        model.addAttribute("fabricantes", serviceFabricante.findByName(name));
        return "showmanufacturers";
    }

    @GetMapping("/mostrarfabricantes/editar/{id}")
    public String showEditConsole(Model model, @PathVariable Long id) {
        model.addAttribute("fabricante", serviceFabricante.getById(id));
        return "addmanufacturer";
    }

    @PostMapping("/mostrarfabricantes/editar/submit")
    public String editConsole(@ModelAttribute("fabricante") Fabricante fabricante) {
        serviceFabricante.edit(fabricante);
        return "redirect:/mostrarfabricantes";
    }

    @GetMapping("/mostrarfabricantes/{id}")
    public String deleteConsole(Model model, @PathVariable Long id) {
        if (serviceFabricante.deleteFabricante(id)) {
            return "redirect:/mostrarfabricantes";
        } else {
            model.addAttribute("error", "No se puede eliminar la consola porque tiene modelos asociados.");
            model.addAttribute("fabricante", serviceFabricante.findAll());
            return "showmanufacturers";
        }

    }

}
