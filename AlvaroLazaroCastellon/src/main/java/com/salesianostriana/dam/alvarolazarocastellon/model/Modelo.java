package com.salesianostriana.dam.alvarolazarocastellon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Lob
    private String description;

    private double precio;

    private int cantidad;

    private int ventas;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaLanzamiento;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate llegadaAlMercado;

    @Lob
    private String imagen;

}
