package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        serviceJuego.addGame(juego);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego")
    public String showGame(Model model) {
        model.addAttribute("juego", serviceJuego.getList());
        return "showgames";
    }

    @GetMapping("/mostrarjuego/editar/{id}")
    public String showEditGame(Model model, @PathVariable Long id) {
        model.addAttribute("juego", serviceJuego.getJuego(id));
        return "editgame";
    }

    @PostMapping("/mostrarjuego/{id}")
    public String editGame(Model model, @PathVariable Long id, @ModelAttribute("juego") Juego j) {
        Juego juego = serviceJuego.getJuego(id);
        juego.setId(id);
        juego.setNombre(j.getNombre());
        juego.setDescription(j.getDescription());
        juego.setPrecio(j.getPrecio());
        juego.setCantidad(j.getCantidad());
        juego.setVentas(j.getVentas());
        juego.setNuevo(j.isNuevo());
        juego.setGenero(j.getGenero());
        juego.setNumJugadores(j.getNumJugadores());
        juego.setPlataforma(j.getPlataforma());
        juego.setFechaLanzamiento(j.getFechaLanzamiento());
        juego.setLlegadaAlMercado(j.getLlegadaAlMercado());
        juego.setRutaImagen(j.getRutaImagen());
        serviceJuego.editGame(juego);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego/{id}")
    public String deleteGame(Model model, @PathVariable Long id) {
        serviceJuego.removeGame(id);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/catalogo")
    public String showCatalogo(Model model) {
        model.addAttribute("juegos", serviceJuego.getList());
        return "catalogo";
    }

}
