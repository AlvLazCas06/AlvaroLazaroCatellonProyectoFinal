package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String showConsoles(Model model, @RequestParam(value = "palabraClave", required = false) String palabraClave) {
        model.addAttribute("consolas", serviceConsola.listAll2(palabraClave));
        model.addAttribute("palabraClave", palabraClave);
        return "showconsoles";
    }

    @GetMapping("/mostrarconsolas/editar/{id}")
    public String showEditConsole(Model model, @PathVariable Long id) {
        model.addAttribute("consola", serviceConsola.getById(id));
        return "addconsole";
    }

    @PostMapping("/mostrarconsolas/editar/submit")
    public String editConsole(@ModelAttribute("consola") Consola c) {
        serviceConsola.edit(c);
        return "redirect:/mostrarconsolas";
    }

    @GetMapping("/mostrarconsolas/{id}")
    public String deleteConsole(Model model, @PathVariable Long id) {
        if (serviceConsola.deleteConsole(id)) {
            return "redirect:/mostrarconsolas";
        } else {
            model.addAttribute("error", "No se puede eliminar la consola porque tiene juegos asociados.");
            model.addAttribute("consolas", serviceConsola.findAll());
            return "showconsoles";
        }

    }

}
