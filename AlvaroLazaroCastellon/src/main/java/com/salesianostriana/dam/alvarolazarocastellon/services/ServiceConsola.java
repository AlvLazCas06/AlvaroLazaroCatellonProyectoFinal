package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ServiceConsola extends BaseServiceImp<Consola, Long, RepositoryConsola> {

    public Consola getById(Long id) {
        return repository.findById(id).get();
    }

    public List<Consola> listAll2(String S) {
        if (S != null) {
            return repository.findAll2(S)
                    .stream()
                    .toList();
        }
        return repository.findAll()
                .stream()
                .toList();
    }

    public boolean deleteConsole(Long id) {
        if (repository.findById(id).get().getJuegos().isEmpty()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Map<Consola, Integer> calculateSalesPerGames () {
        Map<Consola, Integer> map = new HashMap<Consola, Integer>();
        repository.findAll()
                .stream()
                .forEach(
                        consola -> {
                            map.put(consola, consola.getJuegos()
                                    .stream()
                                    .mapToInt(Juego::getVentas)
                                    .sum());
                        }
                );
        return map;
    }

    public Map<Consola, Integer> calculateSalesPerModel () {
        Map<Consola, Integer> map = new HashMap<Consola, Integer>();
        repository.findAll()
                .stream()
                .forEach(
                        consola -> {
                            map.put(consola, consola.getModelos()
                                    .stream()
                                    .mapToInt(Modelo::getVentas)
                                    .sum());
                        }
                );
        return map;
    }

}
