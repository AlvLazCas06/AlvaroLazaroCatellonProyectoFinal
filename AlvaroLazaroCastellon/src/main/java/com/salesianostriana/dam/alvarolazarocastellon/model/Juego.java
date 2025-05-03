package com.salesianostriana.dam.alvarolazarocastellon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Juego {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @Column(length = 3000)
    private String description;

    private double precio;

    private int cantidad;

    private int ventas;

    private String genero;

    private int numJugadores;

    @ManyToOne
    @JoinColumn(name = "console_id")
    private Consola consola;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaLanzamiento;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate llegadaAlMercado;

    private String rutaImagen;

}
