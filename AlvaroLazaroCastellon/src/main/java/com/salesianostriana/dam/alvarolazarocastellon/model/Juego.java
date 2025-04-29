package com.salesianostriana.dam.alvarolazarocastellon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Juego {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String description;

    private double precio;

    private int cantidad;

    private int ventas;

    private boolean nuevo;

    private String genero;

    private int numJugadores;

    private String plataforma;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaLanzamiento;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate llegadaAlMercado;

    private String rutaImagen;

}
