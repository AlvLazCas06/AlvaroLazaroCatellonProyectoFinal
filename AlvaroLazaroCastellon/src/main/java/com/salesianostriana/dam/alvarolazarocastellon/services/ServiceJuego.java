package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceJuego {

    private List<Juego> list = new ArrayList<Juego>();
    private Long nextId = 1L;

    public void addGames(Juego j) {
        j.setId(nextId++);
        list.add(j);
    }

    public List<Juego> getList() {
        return list;
    }

}
