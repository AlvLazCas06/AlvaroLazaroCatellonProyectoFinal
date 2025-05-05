package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerConsola {

    @Autowired
    private ServiceConsola serviceConsola;

    @GetMapping("/mostrarconsolas/añadirconsola")
    public String showGames(Model model) {
        model.addAttribute("consola", new Consola());
        return "addconsole";
    }

    @PostMapping("/mostrarconsolas")
    public String addGame(Model model, @ModelAttribute("consola") Consola consola) {
        serviceConsola.save(consola);
        return "redirect:/mostrarconsolas";
    }

    @GetMapping("/mostrarconsolas")
    public String showConsoles(Model model) {
        model.addAttribute("consolas", serviceConsola.findAll());
        return "showconsoles";
    }

    @GetMapping("/mostrarconsolas/editar/{id}")
    public String showEditConsole(Model model, @PathVariable Long id) {
        model.addAttribute("consola", serviceConsola.findById(id));
        return "editconsole";
    }

    @PostMapping("/mostrarconsolas/{id}")
    public String editConsole(Model model, @PathVariable Long id, @ModelAttribute("consola") Consola c) {
        Consola consola = serviceConsola.findById(id);
        consola.setId(id);
        consola.setNombre(c.getNombre());
        consola.setDescription(c.getDescription());
        consola.setPrecio(c.getPrecio());
        consola.setCantidad(c.getCantidad());
        consola.setVentas(c.getVentas());
        consola.setFechaLanzamiento(c.getFechaLanzamiento());
        consola.setLlegadaAlMercado(c.getLlegadaAlMercado());
        consola.setImagen(c.getImagen());
        serviceConsola.edit(consola);
        return "redirect:/mostrarconsolas";
    }

    @GetMapping("/mostrarconsolas/{id}")
    public String deleteConsole(Model model, @PathVariable Long id) {
        serviceConsola.deleteById(id);
        return "redirect:/mostrarconsolas";
    }
}
