package com.salesianostriana.dam.alvarolazarocastellon.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "fabricante", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Modelo> modelos;

}
