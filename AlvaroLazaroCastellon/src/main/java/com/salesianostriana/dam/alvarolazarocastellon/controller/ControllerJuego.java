package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ControllerJuego {

    @Autowired
    private ServiceJuego serviceJuego;

    @Autowired
    private ServiceConsola serviceConsola;

    @GetMapping("/mostrarjuego/añadirjuego")
    public String showGames(Model model) {
        Juego juego = new Juego();
        model.addAttribute("juego", juego);
        model.addAttribute("consolas", serviceConsola.findAll());
        return "addgames";
    }

    @PostMapping("/mostrarjuego")
    public String addGame(@ModelAttribute("juego") Juego juego) {
        if (juego.getConsola() != null) {
            Consola consola = serviceConsola.findById(juego.getConsola().getId());
            juego.setConsola(consola);
        }
        serviceJuego.save(juego);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego")
    public String showGame(Model model, @ModelAttribute("palabraClave") String palabraClave) {
        List<Juego> juegos = serviceJuego.listAll2(palabraClave);
        model.addAttribute("juego", juegos);
        model.addAttribute("palabraClave", palabraClave);
        return "showgames";
    }

    @GetMapping("/mostrarjuego/editar/{id}")
    public String showEditGame(Model model, @PathVariable Long id) {
        model.addAttribute("juego", serviceJuego.findById(id));
        model.addAttribute("consolas", serviceConsola.findAll());
        return "addgame";
    }

    @PostMapping("/mostrarjuego/editar/submit")
    public String editGame(@ModelAttribute("juego") Juego j) {

        serviceJuego.edit(j);

        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego/{id}")
    public String deleteGame(@PathVariable Long id) {
        serviceJuego.deleteById(id);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/catalogo")
    public String showCatalogo(Model model, @Param("palabraClave") String palabraClave, @Param("orden") String orden, @Param("consola") String consola) {
        List<Juego> juegos = serviceJuego.listAll(palabraClave);

        if (consola != null && !consola.isEmpty()) {
            juegos = serviceJuego.findByConsole(consola);
        }

        if ("nombre".equalsIgnoreCase(orden)) {
            juegos = serviceJuego.orderByName(palabraClave);
        }

        model.addAttribute("juegos", juegos);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("orden", orden);
        model.addAttribute("consola", consola);
        return "catalogo";
    }

    @GetMapping("/próximamente")
    public String showNextRelease(Model model) {
        List<Juego> juegosProximos = serviceJuego.findNotSell();
        model.addAttribute("juegosProximos", juegosProximos);
        return "proximamente";
    }

    @GetMapping("/novedades")
    public String showNovedades(Model model) {

        model.addAttribute("novedades", serviceJuego.findNewGames());
        return "novedades";
    }

}
