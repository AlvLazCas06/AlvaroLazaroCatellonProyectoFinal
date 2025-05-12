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
    public String showConsoles(Model model) {
        model.addAttribute("consolas", serviceConsola.findAll());
        return "showconsoles";
    }

    @GetMapping("/mostrarconsolas/editar/{id}")
    public String showEditConsole(Model model, @PathVariable Long id) {
        model.addAttribute("consola", serviceConsola.findById(id));
        return "editconsole";
    }

    @PostMapping("/mostrarconsolas/editar/submit")
    public String editConsole(@ModelAttribute("consola") Consola c) {
        serviceConsola.edit(c);
        return "redirect:/mostrarconsolas";
    }

    @GetMapping("/mostrarconsolas/{id}")
    public String deleteConsole(Model model, @PathVariable Long id) {
        serviceConsola.deleteById(id);
        return "redirect:/mostrarconsolas";
    }

    @GetMapping("/catalogoconsolas")
    public String showCatalogo(Model model, @ModelAttribute("palabraClave") String palabraClave) {
        List<Consola> consolas = serviceConsola.listAll(palabraClave);
        model.addAttribute("consolas", consolas);
        model.addAttribute("palabraClave", palabraClave);
        return "catalogoconsola";
    }

    @GetMapping("/proximamenteconsolas")
    public String showNextRelease(Model model) {
        List<Consola> consolasProximas = serviceConsola.findNotSell();
        model.addAttribute("consolasProximas", consolasProximas);
        return "proximamenteconsolas";
    }

    @GetMapping("/novedadesconsolas")
    public String showNovedades(Model model) {
        List<Consola> consolasNovedades = serviceConsola.findNewConsoles();
        model.addAttribute("consolasNovedades", consolasNovedades);
        return "novedadesconsolas";
    }
}
