package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControllerIndex {

    @Autowired
    private ServiceJuego serviceJuego;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("masvendido", serviceJuego.findMaxSell().orElse(new Juego()));
        model.addAttribute("threeMaxSell", serviceJuego.findThreeMaxSell());
        model.addAttribute("newGame", serviceJuego.findNewGame());
        return "main";
    }

    @GetMapping("/quienessomos")
    public String ShowWhoAreWe(Model model) {
        return "quienessomos";
    }
}
