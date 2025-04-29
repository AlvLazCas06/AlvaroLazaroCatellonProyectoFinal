package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerIndex {

    @GetMapping("/")
    public String index(Model model) {
        Juego juego = new Juego();
        model.addAttribute("masvendido", juego);
        return "main";
    }

}
