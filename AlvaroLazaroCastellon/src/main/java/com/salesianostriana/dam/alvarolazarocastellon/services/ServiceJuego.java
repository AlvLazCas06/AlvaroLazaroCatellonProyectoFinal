package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceJuego {

    @Autowired
    private RepositoryJuego repositoryJuego;

    public void addGames(Juego j) {
        repositoryJuego.save(j);
    }

    public List<Juego> getList() {
        return repositoryJuego.findAll();
    }

}
