package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControllerIndex {

    @Autowired
    private ServiceJuego serviceJuego;

    @Autowired
    private ServiceConsola serviceConsola;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("masVendido", serviceJuego.findMaxSell().orElse(new Juego()));
        model.addAttribute("threeMaxSell", serviceJuego.findThreeMaxSell());
        model.addAttribute("newGame", serviceJuego.findNewGame());
        model.addAttribute("consolaMasVendida", serviceConsola.findMaxSell().orElse(new Consola()));
        model.addAttribute("threeMaxSellConsole", serviceConsola.findThreeMaxSell());
        return "main";
    }

    @GetMapping("/quienessomos")
    public String ShowWhoAreWe(Model model) {
        return "quienessomos";
    }
}
