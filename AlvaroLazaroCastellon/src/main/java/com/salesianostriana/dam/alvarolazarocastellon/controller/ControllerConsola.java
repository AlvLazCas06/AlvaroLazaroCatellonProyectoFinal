package com.salesianostriana.dam.alvarolazarocastellon.controller;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.services.ServiceConsola;
import com.salesianostriana.dam.alvarolazarocastellon.util.ConsolaExportPDF;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class ControllerConsola {

    @Autowired
    private ServiceConsola serviceConsola;

    @GetMapping("/mostrarconsolas/añadirconsola")
    public String showGames(Model model) {
        model.addAttribute("consola", new Consola());
        return "addconsole";
    }

    @PostMapping("/mostrarconsolas")
    public String addGame(@ModelAttribute("consola") Consola consola) {
        serviceConsola.save(consola);
        return "redirect:/mostrarconsolas";
    }

    @GetMapping("/mostrarconsolas")
    public String showConsoles(Model model, @RequestParam(value = "palabraClave", required = false) String palabraClave) {
        model.addAttribute("consolas", serviceConsola.listAll2(palabraClave));
        model.addAttribute("palabraClave", palabraClave);
        return "showconsoles";
    }

    @GetMapping("/mostrarconsolas/editar/{id}")
    public String showEditConsole(Model model, @PathVariable Long id) {
        model.addAttribute("consola", serviceConsola.getById(id));
        return "addconsole";
    }

    @PostMapping("/mostrarconsolas/editar/submit")
    public String editConsole(@ModelAttribute("consola") Consola c) {
        serviceConsola.edit(c);
        return "redirect:/mostrarconsolas";
    }

    @GetMapping("/mostrarconsolas/{id}")
    public String deleteConsole(Model model, @PathVariable Long id) {
        if (serviceConsola.deleteConsole(id)) {
            return "redirect:/mostrarconsolas";
        } else {
            model.addAttribute("error", "No se puede eliminar la consola porque tiene juegos asociados.");
            model.addAttribute("consolas", serviceConsola.findAll());
            return "showconsoles";
        }

    }

    @GetMapping("/eportarPDFconsolas")
    public void exportGameListInPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String header = "Content-Disposition";
        String value = "attachment; filename=\"consolas.pdf\"";

        response.setHeader(header, value);

        List<Consola> modelos = serviceConsola.findAll();
        ConsolaExportPDF exportPDF = new ConsolaExportPDF(modelos);
        exportPDF.exportDocument(response);
    }

    @GetMapping("/ventas/juegos")
    public String showSalesOfGames(Model model) {
        model.addAttribute("sales", serviceConsola.calculateSalesPerGames());
        return "ventas";
    }

    @GetMapping("/ventas/modelos")
    public String showSalesOfModels(Model model) {
        model.addAttribute("sales", serviceConsola.calculateSalesPerModel());
        return "ventasModelo";
    }

}
