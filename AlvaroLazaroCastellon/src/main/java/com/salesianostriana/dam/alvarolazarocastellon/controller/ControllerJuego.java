package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceJuego;
import com.salesianostriana.dam.alvarolazarocastellon.util.JuegoExportPDF;
import com.salesianostriana.dam.alvarolazarocastellon.util.PageRender;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
            Consola consola = serviceConsola.getById(juego.getConsola().getId());
            juego.setConsola(consola);
        }
        serviceJuego.save(juego);
        return "redirect:/mostrarjuego";
    }

    @GetMapping("/mostrarjuego")
    public String showGame(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(value = "palabraClave", required = false) String palabraClave,
                           @RequestParam(value = "nombre", required = false) String nombre) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Juego> juego = serviceJuego.findAllPage(palabraClave, nombre, pageRequest);
        PageRender<Juego> pageRender = new PageRender<>("/mostrarjuego", juego);

        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("nombre", nombre);
        model.addAttribute("page", juego);
        model.addAttribute("pageRender", pageRender);
        model.addAttribute("consolas", serviceConsola.findAll());
        return "showgames";
    }

    @GetMapping("/mostrarjuego/editar/{id}")
    public String showEditGame(Model model, @PathVariable Long id) {
        model.addAttribute("juego", serviceJuego.getById(id));
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
                                @RequestParam(required = false) String genero,
                                @RequestParam(required = false) String palabraClave) {
        List<Juego> juegos;

        if (sort == null || sort.isEmpty()) {
            juegos = serviceJuego.listAll(palabraClave);
        } else {
            juegos = serviceJuego.sortGames(sort);
        }

        if (genero != null && !genero.isEmpty()) {
            juegos = serviceJuego.findByGenre(genero);
        }

        if (consola != null && !consola.isEmpty()) {
            juegos = serviceJuego.findByConsole(consola, palabraClave);
        }

        model.addAttribute("juegos", juegos);
        model.addAttribute("orden", sort);
        model.addAttribute("genero", genero);
        model.addAttribute("consola", consola);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("consolas", serviceConsola.findAll());

        return "catalogo";
    }

    @GetMapping("/próximamente")
    public String showNextRelease(Model model,
                                  @RequestParam(required = false) String palabraClave,
                                  @RequestParam(required = false) String consola,
                                  @RequestParam(required = false) String sort,
                                  @RequestParam(required = false) String genero) {
        List<Juego> juegos;

        if (sort == null || sort.isEmpty()) {
            juegos = serviceJuego.findNotSell(palabraClave);
        } else {
            juegos = serviceJuego.sortGamesOnNotSell(sort);
        }

        if (genero != null && !genero.isEmpty()) {
            juegos = serviceJuego.findByGenreOnNotSell(genero);
        }

        if (consola != null && !consola.isEmpty()) {
            juegos = serviceJuego.findByConsoleOnNotSell(consola, palabraClave);
        }

        model.addAttribute("juegosProximos", juegos);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("genero", genero);
        model.addAttribute("sort", sort);
        model.addAttribute("consolas", serviceConsola.findAll());
        model.addAttribute("consola", consola);
        return "proximamente";
    }

    @GetMapping("/novedades")
    public String showNewGames(Model model,
                               @RequestParam(required = false) String palabraClave,
                               @RequestParam(required = false) String sort,
                               @RequestParam(required = false) String genero,
                               @RequestParam(required = false) String consola) {
        List<Juego> juegos;

        if (sort == null || sort.isEmpty()) {
            juegos = serviceJuego.findNewGames(palabraClave);
        } else {
            juegos = serviceJuego.sortGamesOnNews(sort);
        }

        if (genero != null && !genero.isEmpty()) {
            juegos = serviceJuego.findByGenreOnNews(genero);
        }

        if (consola != null && !consola.isEmpty()) {
            juegos = serviceJuego.findByConsoleOnNews(consola, palabraClave);
        }

        model.addAttribute("novedades", juegos);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("genero", genero);
        model.addAttribute("sort", sort);
        model.addAttribute("consolas", serviceConsola.findAll());
        model.addAttribute("consola", consola);
        return "novedades";
    }

    @GetMapping({"/catalogo/venta/{id}", "/novedades/venta/{id}", "/proximamente/reserva/{id}"})
    public String showSale(Model model, @PathVariable Long id) {
        model.addAttribute("juegoAVender", serviceJuego.getById(id));
        return "ventajuego";
    }

    @GetMapping("/sort")
    public String sendSort(@RequestParam(name = "orden") String sort,
                           @RequestParam(required = false) String palabraClave,
                           @RequestParam(required = false) String genero,
                           RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("orden", sort);
        redirectAttributes.addAttribute("palabraClave", palabraClave);
        redirectAttributes.addAttribute("genero", genero);
        return "redirect:/catalogo";
    }

    @GetMapping("/eportarPDF")
    public void exportGameListInPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String header = "Content-Disposition";
        String value = "attachment; filename=\"juegos.pdf\"";

        response.setHeader(header, value);

        List<Juego> juegos = serviceJuego.findAll();
        JuegoExportPDF exportPDF = new JuegoExportPDF(juegos);
        exportPDF.exportDocument(response);
    }

    @GetMapping("/descuento/juego")
    public String setDiscount(Model model,
                              @RequestParam(required = false, defaultValue = "0") int descuentoNormal,
                              @RequestParam(required = false, defaultValue = "0") int descuentoLlegada2010,
                              @RequestParam(required = false, defaultValue = "0") int descuentoLlegadaHoy) {
        model.addAttribute("descuentoNormal", descuentoNormal);
        model.addAttribute("descuentoLlegada2010", descuentoLlegada2010);
        model.addAttribute("descuentoLlegadaHoy", descuentoLlegadaHoy);
        return "descuento";
    }

    @PostMapping("/descuento/juego")
    public String submitDiscount(@RequestParam(required = false, defaultValue = "0") int descuentoNormal,
                                 @RequestParam(required = false, defaultValue = "0") int descuentoLlegada2010,
                                 @RequestParam(required = false, defaultValue = "0") int descuentoLlegadaHoy) {
        serviceJuego.applyDiscountByYear(descuentoNormal, descuentoLlegada2010, descuentoLlegadaHoy);
        return "redirect:/descuento/juego";
    }
}
