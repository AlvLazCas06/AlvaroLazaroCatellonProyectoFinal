package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryJuego;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceJuego extends BaseServiceImp<Juego, Long, RepositoryJuego> {

    public List<Juego> listAll(String S) {
        if (S != null) {
            return repository.findAll(S)
                    .stream()
                    .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Juego> listAll2(String S) {
        if (S != null) {
            return repository.findAll2(S)
                    .stream()
                    .toList();
        }
        return repository.findAll()
                .stream()
                .toList();
    }

    public Optional<Juego> findMaxSell() {
       return repository.findAll()
               .stream()
               .max((j1, j2) -> Integer.compare(j1.getVentas(), j2.getVentas()));
    }

    public List<Juego> orderByName(String palabraClave) {
        if (palabraClave != null) {
            return repository.findAll(palabraClave).stream()
                    .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .sorted((j1, j2) -> j1.getNombre().compareToIgnoreCase(j2.getNombre()))
                    .toList();
        }
        return repository.findAll().stream()
                .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .sorted((j1, j2) -> j1.getNombre().compareToIgnoreCase(j2.getNombre()))
                .toList();
    }

    public List<Juego> findByConsole(String consola) {
        return repository.findAll().stream()
                .filter(j -> j.getConsola() != null && j.getConsola().getNombre().equalsIgnoreCase(consola))
                .toList();
    }

    public List<Juego> findNotSell() {
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now())).toList();
    }

    public List<Juego> findNewGames() {
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public List<Juego> findThreeMaxSell() {
        return repository.findAll()
                .stream()
                .sorted((j1, j2) -> Integer.compare(j2.getVentas(), j1.getVentas()))
                .limit(3)
                .toList();
    }

    public List<Juego> sortGames(String sort, String palabraClave) {
        List<Juego> juegos;
        if (palabraClave != null) {
            juegos = repository.findAll(palabraClave).stream()
                    .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }

        if (sort == null) {
            juegos = repository.findAll().stream()
                    .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }

        switch (sort) {
            case "priceASC" -> {
                juegos = repository.orderByPrecioASC().stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            case "priceDESC" -> {
                juegos = repository.orderByPrecioDESC().stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            case "A-Z" -> {
                juegos = repository.orderByNombreASC().stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            case "Z-A" -> {
                juegos = repository.orderByNombreDESC().stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            default -> {
                juegos = repository.findAll().stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
        }
        return juegos;
    }

    public double applyDiscountByYear(Long id) {
        return repository.findById(id)
                .stream()
                .mapToDouble(Juego::getPrecio)
                .sum()
                -
                repository.findById(id)
                .stream()
                .filter(j -> j.getFechaLanzamiento().getYear() < 2010)
                .mapToDouble(Juego::getPrecio)
                .sum() * 0.10;
    }

    /*
    public List<Juego> showOffers() {
        Random idrandom = new Random(System.nanoTime());
        return repository.findAll().stream().filter(j -> j.getConsola().getId() == id.nextLong(repository.findAll().size() - 0 + 1)).filter().toList();
    }
    */

}
