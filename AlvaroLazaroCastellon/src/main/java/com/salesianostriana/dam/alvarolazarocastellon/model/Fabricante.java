package com.salesianostriana.dam.alvarolazarocastellon.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Fabricante implements Comparable<Fabricante> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    @OneToMany(mappedBy = "fabricante", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Modelo> modelos;

    @Override
    public int compareTo(Fabricante o) {
        return Long.compare(this.id, o.id);
    }
}
