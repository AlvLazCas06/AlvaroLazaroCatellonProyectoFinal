package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryJuego;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceJuego extends BaseService<Juego, Long, RepositoryJuego> {

    public List<Juego> listAll(String S) {
        if (S != null) {
            return repository.findAll(S).stream().toList();
        }
        return repository.findAll().stream().toList();
    }

}
