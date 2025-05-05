package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryJuego;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceJuego extends BaseService<Juego, Long, RepositoryJuego> {

    public List<Juego> listAll(String S) {
        if (S != null) {
            return repository.findAll(S).stream().toList();
        }
        return repository.findAll().stream().toList();
    }

    public Optional<Juego> findMaxSell() {
       return repository.findAll().stream().max((j1, j2) -> j1.getVentas() > j2.getVentas() ? 1 : -1);
    }

    public List<Juego> orderByName() {
        return repository.findAll().stream().sorted((j1, j2) -> j1.getNombre().compareToIgnoreCase(j2.getNombre())).toList();
    }

    public List<Juego> findNotSell() {
        return repository.findAll().stream().filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now())).toList();
    }

    public List<Juego> findNewGames() {
        return repository.findAll().stream().filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now())).toList();
    }

}
