package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

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
        if (repository.findById(id).get().getJuegos().isEmpty()
                && repository.findById(id).get().getModelos().isEmpty()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Map<Consola, Double> calculateAveragePricePerGames() {
        Map<Consola, Double> map = new LinkedHashMap<Consola, Double>();
        repository.findAll()
                .forEach(
                        consola -> map.put(consola, consola.getJuegos()
                                .stream()
                                .mapToDouble(Juego::getPrecio)
                                .average()
                                .getAsDouble())
                );
        return map;
    }

    public double calculateAllAveragesGames() {
        List<Double> calculos = new ArrayList<Double>(calculateAveragePricePerGames().values());
        return calculos.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public Map<Consola, Double> calculateAveragePricePerModel() {
        Map<Consola, Double> map = new LinkedHashMap<Consola, Double>();
        repository.findAll()
                .forEach(
                        consola -> map.put(consola, consola.getModelos()
                                .stream()
                                .mapToDouble(Modelo::getPrecio)
                                .average()
                                .getAsDouble())
                );
        return map;
    }

    public double calculateAllAveragesModels() {
        List<Double> calculos = new ArrayList<Double>(calculateAveragePricePerModel().values());
        return calculos.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

}
