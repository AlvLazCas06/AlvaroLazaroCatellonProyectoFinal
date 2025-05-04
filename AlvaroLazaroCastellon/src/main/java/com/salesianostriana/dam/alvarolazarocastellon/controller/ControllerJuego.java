package com.salesianostriana.dam.alvarolazarocastellon.controller;

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
    public String addGame(Model model, @ModelAttribute("juego") Juego juego) {
        serviceJuego.save(juego);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego")
    public String showGame(Model model) {
        model.addAttribute("juego", serviceJuego.findAll());
        return "showgames";
    }

    @GetMapping("/mostrarjuego/editar/{id}")
    public String showEditGame(Model model, @PathVariable Long id) {
        model.addAttribute("juego", serviceJuego.findById(id));
        model.addAttribute("consolas", serviceConsola.findAll());
        return "editgame";
    }

    @PostMapping("/mostrarjuego/{id}")
    public String editGame(Model model, @PathVariable Long id, @ModelAttribute("juego") Juego j) {
        Juego juego = serviceJuego.findById(id);
        juego.setId(id);
        juego.setNombre(j.getNombre());
        juego.setDescription(j.getDescription());
        juego.setPrecio(j.getPrecio());
        juego.setCantidad(j.getCantidad());
        juego.setVentas(j.getVentas());
        juego.setGenero(j.getGenero());
        juego.setNumJugadores(j.getNumJugadores());
        juego.setConsola(j.getConsola());
        juego.setFechaLanzamiento(j.getFechaLanzamiento());
        juego.setLlegadaAlMercado(j.getLlegadaAlMercado());
        juego.setRutaImagen(j.getRutaImagen());
        serviceJuego.edit(juego);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego/{id}")
    public String deleteGame(Model model, @PathVariable Long id) {
        serviceJuego.deleteById(id);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/catalogo")
    public String showCatalogo(Model model, @Param("palabraClave") String palabraClave) {
        List<Juego> juegos = serviceJuego.listAll(palabraClave);
        model.addAttribute("juegos", juegos);
        model.addAttribute("palabraClave", palabraClave);
        return "catalogo";
    }

}
