package com.salesianostriana.dam.alvarolazarocastellon.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Consola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "consola", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Modelo> modelos = new ArrayList<Modelo>();

    @Lob
    private String description;

    @Lob
    private String imagen;

    @OneToMany(mappedBy = "consola", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Juego> juegos = new ArrayList<Juego>();

    public void addJuego(Juego juego) {
        juegos.add(juego);
        juego.setConsola(this);
    }

    public void removeJuego(Juego juego) {
        juegos.remove(juego);
        juego.setConsola(null);
    }

    public void addModelo(Modelo modelo) {
        modelos.add(modelo);
        modelo.setConsola(this);
    }

    public void removeModelo(Modelo modelo) {
        modelos.remove(modelo);
        modelo.setConsola(null);
    }

    @Override
    public String toString() {
        return "Consola{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", modelos=" + modelos +
                ", description='" + description + '\'' +
                ", imagen='" + imagen + '\'' +
                ", juegos=" + juegos +
                '}';
    }
}
