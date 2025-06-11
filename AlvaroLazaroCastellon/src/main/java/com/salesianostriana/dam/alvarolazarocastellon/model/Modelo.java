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
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    private double precio;

    private int cantidad;

    private int ventas;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaLanzamiento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate llegadaAlMercado;

    @Lob
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "console_id")
    private Consola consola;

    public void setConsola(Consola consola) {
        if (this.consola != null) {
            this.consola.getModelos().remove(this);
        }
        this.consola = consola;
        if (consola != null) {
            consola.getModelos().add(this);
        }
    }

    public void setFabricante(Fabricante fabricante) {
        if (this.fabricante != null) {
            this.fabricante.getModelos().remove(this);
        }
        this.fabricante = fabricante;
        if (fabricante != null) {
            fabricante.getModelos().add(this);
        }
    }

}
