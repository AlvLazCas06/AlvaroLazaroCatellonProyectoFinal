package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryJuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceJuego {

    @Autowired
    private RepositoryJuego repositoryJuego;

    public void addGame(Juego j) {
        repositoryJuego.save(j);
    }

    public List<Juego> getList() {
        return repositoryJuego.findAll().stream().toList();
    }

    public Juego getJuego(Long id) {
        return repositoryJuego.findById(id).get();
    }

    public void editGame(Juego j) {
        repositoryJuego.save(j);
    }

    public void removeGame(Long id) {
        repositoryJuego.deleteById(id);
    }

}
