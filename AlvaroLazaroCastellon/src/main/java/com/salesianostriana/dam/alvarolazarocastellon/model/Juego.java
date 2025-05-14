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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Lob
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

    @Column(length = 3000)
    private String rutaImagen;

    public void setConsola(Consola consola) {
        if (this.consola != null) {
            this.consola.getJuegos().remove(this);
        }
        this.consola = consola;
        if (consola != null) {
            consola.getJuegos().add(this);
        }
    }

}
