package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerJuego {

    @Autowired
    private ServiceJuego serviceJuego;

    @GetMapping("/añadirjuego")
    public String showGames(Model model) {

        Juego juego = new Juego();
        model.addAttribute("juego", juego);

        return "addgames";
    }

    @PostMapping("/mostrarjuego")
    public String addGame(Model model, @ModelAttribute("juego") Juego juego) {
        serviceJuego.addGames(juego);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego")
    public String showGame(Model model) {
        model.addAttribute("juego", serviceJuego.getList());
        return "showgames";
    }

    @GetMapping("/catalogo")
    public String showCatalogo(Model model) {
        model.addAttribute("juegos", serviceJuego.getList());
        return "catalogo";
    }

}
