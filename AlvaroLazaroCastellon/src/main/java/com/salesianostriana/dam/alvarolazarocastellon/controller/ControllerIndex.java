package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerIndex {

    @Autowired
    private ServiceJuego serviceJuego;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("masvendido", serviceJuego.findMaxSell().orElse(new Juego()));
        model.addAttribute("threeMaxSell", serviceJuego.findThreeMaxSell());
        return "main";
    }

    @PostMapping("/carrito/agregar/{id}")
    public String addGameToCart(@PathVariable Long id, @RequestParam int cantidad, Model model) {
        Juego juego = serviceJuego.findById(id);
        juego.setCantidad(cantidad);
        if (juego != null) {
            model.addAttribute("juego", juego);
            return "redirect:/";
        } else {
            return "error";
        }
    }

    @GetMapping("/carrito")
    public String showCarrito() {
        List<Juego> juegos = new ArrayList<Juego>();
        return "carrito";
    }

}
