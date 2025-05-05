package com.salesianostriana.dam.alvarolazarocastellon.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Consola {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String modelo;

    @Column(length = 3000)
    private String description;

    private String fabricante;

    private double precio;

    private int cantidad;

    private int ventas;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaLanzamiento;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate llegadaAlMercado;

    private String imagen;

    @OneToMany
    private List<Juego> juegos = new ArrayList<Juego>();

}
