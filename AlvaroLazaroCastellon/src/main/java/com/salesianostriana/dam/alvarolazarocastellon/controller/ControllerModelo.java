package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Fabricante;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceFabricante;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceModelo;
import com.salesianostriana.dam.alvarolazarocastellon.util.ModeloExportPDF;
import com.salesianostriana.dam.alvarolazarocastellon.util.PageRender;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ControllerModelo {

    @Autowired
    private ServiceModelo serviceModelo;

    @Autowired
    private ServiceConsola serviceConsola;

    @Autowired
    private ServiceFabricante serviceFabricante;

    @GetMapping("/mostrarmodelos/añadirmodelo")
    public String showGames(Model model) {
        Modelo modelo = new Modelo();
        model.addAttribute("modelo", modelo);
        model.addAttribute("consolas", serviceConsola.findAll());
        model.addAttribute("fabricantes", serviceFabricante.findAll());
        return "addmodel";
    }

    @PostMapping("/mostrarmodelos")
    public String addGame(@ModelAttribute("modelo") Modelo modelo) {
        if (modelo.getConsola() != null && modelo.getFabricante() != null) {
            Consola consola = serviceConsola.getById(modelo.getConsola().getId());
            modelo.setConsola(consola);
            Fabricante fabricante = serviceFabricante.getById(modelo.getFabricante().getId());
            modelo.setFabricante(fabricante);
        }
        serviceModelo.save(modelo);
        return "redirect:/mostrarmodelos";
    }

    @GetMapping("/mostrarmodelos/editar/{id}")
    public String showEditGame(Model model, @PathVariable Long id) {
        model.addAttribute("modelo", serviceModelo.getById(id));
        model.addAttribute("consolas", serviceConsola.findAll());
        model.addAttribute("fabricantes", serviceFabricante.findAll());
        return "addmodel";
    }

    @PostMapping("/mostrarmodelos/editar/submit")
    public String editGame(@ModelAttribute("modelo") Modelo modelo) {

        serviceModelo.edit(modelo);

        return "redirect:/mostrarmodelos";
    }

    @GetMapping("/mostrarmodelos/{id}")
    public String deleteGame(@PathVariable Long id) {
        serviceModelo.deleteById(id);
        return "redirect:/mostrarmodelos";
    }

    @GetMapping("/mostrarmodelos")
    public String showModel(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @ModelAttribute("palabraClave") String palabraClave) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Modelo> modelos = serviceModelo.findAllPage(palabraClave, pageRequest);
        PageRender<Modelo> pageRender = new PageRender<>("/mostrarmodelos", modelos);

        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("page", modelos);
        model.addAttribute("pageRender", pageRender);
        return "showmodels";
    }

    @GetMapping("/catalogoconsolas")
    public String showCatalogo(Model model,
                               @ModelAttribute("palabraClave") String palabraClave,
                               @RequestParam(value = "fabricante", required = false) String fabricante) {
        List<Modelo> consolas = serviceModelo.listAll(palabraClave);
        if (fabricante != null) {
            consolas = serviceModelo.findByFabricante(fabricante);
        }
        model.addAttribute("consolas", consolas);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("fabricante", fabricante);
        return "catalogoconsola";
    }

    @GetMapping("/proximamenteconsolas")
    public String showNextRelease(Model model,
                                  @ModelAttribute("palabraClave") String palabraClave,
                                  @RequestParam(value = "fabricante", required = false) String fabricante) {
        List<Modelo> consolasProximas = serviceModelo.findNotSell(palabraClave);
        if (fabricante != null) {
            consolasProximas = serviceModelo.findByFabricanteOnNotSell(fabricante);
        }
        model.addAttribute("consolasProximas", consolasProximas);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("fabricante", fabricante);
        return "proximamenteconsolas";
    }

    @GetMapping("/novedadesconsolas")
    public String showNovedades(Model model,
                                @RequestParam(value = "palabraClave", required = false) String palabraClave,
                                @RequestParam(value = "fabricante", required = false) String fabricante) {
        List<Modelo> consolasNovedades = serviceModelo.findNewConsoles(palabraClave);
        if (fabricante != null) {
            consolasNovedades = serviceModelo.findByFabricanteOnNewConsole(fabricante);
        }
        model.addAttribute("consolasNovedades", consolasNovedades);
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("fabricante", fabricante);
        return "novedadesconsolas";
    }

    @GetMapping({"/catalogoconsolas/venta/{id}", "/novedadesconsolas/venta/{id}", "/proximamenteconsolas/reserva/{id}"})
    public String showSale(Model model, @PathVariable Long id) {
        model.addAttribute("consolaAVender", serviceModelo.getById(id));
        model.addAttribute("descuento", serviceModelo.applyDiscount(id));
        return "ventaConsola";
    }

    @GetMapping("/eportarPDFmodelos")
    public void exportGameListInPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String header = "Content-Disposition";
        String value = "attachment; filename=\"modelos.pdf\"";

        response.setHeader(header, value);

        List<Modelo> modelos = serviceModelo.findAll();
        ModeloExportPDF exportPDF = new ModeloExportPDF(modelos);
        exportPDF.exportDocument(response);
    }

}
