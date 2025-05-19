package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "addgames";
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
    public String showCatalogue(Model model,
                                @RequestParam(required = false, name = "orden") String sort,
                                @RequestParam(required = false) String consola,
                                @RequestParam(required = false) String palabraClave) {
        List<Juego> juegos;

        if (sort == null || sort.isEmpty()) {
            juegos = serviceJuego.listAll(palabraClave); // Obtener todos los juegos si no hay orden
        } else {
            juegos = serviceJuego.sortGames(sort); // Ordenar según el parámetro
        }

        // Filtrar por consola si se proporciona
        if (consola != null && !consola.isEmpty()) {
            juegos = serviceJuego.findByConsole(consola, palabraClave);
        }

        model.addAttribute("juegos", juegos);
        model.addAttribute("orden", sort);
        model.addAttribute("consola", consola);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("consolas", serviceConsola.findAll());

        return "catalogo";
    }

    @GetMapping("/próximamente")
    public String showNextRelease(Model model) {
        model.addAttribute("juegosProximos", serviceJuego.findNotSell());
        return "proximamente";
    }

    @GetMapping("/novedades")
    public String showNewGames(Model model) {
        model.addAttribute("novedades", serviceJuego.findNewGames());
        return "novedades";
    }

    @GetMapping({"/catalogo/venta/{id}", "/novedades/venta/{id}"})
    public String showSale(Model model, @PathVariable Long id) {
        model.addAttribute("juegoAVender", serviceJuego.findById(id));
        model.addAttribute("descuento", serviceJuego.applyDiscountByYear(id));
        return "ventajuego";
    }

    @GetMapping("/sort")
    public String sendSort(@RequestParam(name = "orden") String sort, @RequestParam(required = false) String palabraClave, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("orden", sort);
        redirectAttributes.addAttribute("palabraClave", palabraClave);
        return "redirect:/catalogo";
    }

}
