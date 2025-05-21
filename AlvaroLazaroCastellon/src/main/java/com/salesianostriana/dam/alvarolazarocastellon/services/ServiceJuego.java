package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryJuego;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
                .filter(j -> j.getVentas() > 0
                        && j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .max((j1, j2) -> Integer.compare(j1.getVentas(), j2.getVentas()));
    }

    public List<Juego> findByGenre(String genero) {
        if (genero != null) {
            return repository.findJuegoByGeneroIgnoreCase(genero)
                    .stream()
                    .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Juego> findByConsole(String consola, String palabraClave) {
        if (palabraClave != null){
            return repository.findAll(palabraClave).stream()
                    .filter(j -> j.getConsola() != null
                            && j.getConsola().getNombre().equalsIgnoreCase(consola)
                            && j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }
        return repository.findAll().stream()
                .filter(j -> j.getConsola() != null
                        && j.getConsola().getNombre().equalsIgnoreCase(consola)
                        && j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Juego> findNotSell(String palabraClave) {

        if (palabraClave != null) {
            return repository.findAll(palabraClave)
                    .stream()
                    .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                    .toList();
        }

        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Juego> findNewGames(String palabraClave) {

        if (palabraClave != null) {
            return repository.findAll(palabraClave)
                    .stream()
                    .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                    .toList();
        }

        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public Juego findNewGame() {
        Random index = new Random(System.nanoTime());
        List<Juego> juegos = repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();

        if (!juegos.isEmpty()) {
            return juegos.get(index.nextInt(juegos.size()));
        }
        return null;
    }

    public List<Juego> findThreeMaxSell() {
        return repository.findAll()
                .stream()
                .sorted((j1, j2) -> Integer.compare(j2.getVentas(), j1.getVentas()))
                .filter(j -> j.getVentas() > 0
                        && !j.equals(findMaxSell().get()))
                .limit(3)
                .toList();
    }

    public List<Juego> sortGames(String sort) {
        List<Juego> juegos;

        switch (sort) {
            case "priceASC" -> {
                juegos = repository.ordenarPorPrecioASC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            case "priceDESC" -> {
                juegos = repository.ordenarPorPrecioDESC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            case "A-Z" -> {
                juegos = repository.ordenarPorNombreASC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            case "Z-A" -> {
                juegos = repository.ordenarPorNombreDESC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
            default -> {
                juegos = repository.findAll()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                        .toList();
            }
        }

        return juegos;
    }

    public double applyDiscountByYear(Long id) {
        double descuentoNormal = 10, descuentoLlegada2010 = 20, descuentoLlegadaHoy = 15;

        if (repository.findById(id).get().getLlegadaAlMercado().isEqual(LocalDate.now())
                && repository.findById(id).get().getFechaLanzamiento().getYear() < 2010) {
            return repository.findById(id)
                    .stream()
                    .mapToDouble(Juego::getPrecio)
                    .sum()
                    -
                    repository.findById(id)
                            .stream()
                            .mapToDouble(Juego::getPrecio)
                            .sum() * (descuentoLlegada2010 / 100);
        }

        if (repository.findById(id).get().getLlegadaAlMercado().isEqual(LocalDate.now())
                && repository.findById(id).get().getFechaLanzamiento().getYear() >= 2010) {
            return repository.findById(id)
                    .stream()
                    .mapToDouble(Juego::getPrecio)
                    .sum()
                    -
                    repository.findById(id)
                            .stream()
                            .mapToDouble(Juego::getPrecio)
                            .sum() * (descuentoLlegadaHoy / 100);
        }

        return repository.findById(id)
                .stream()
                .mapToDouble(Juego::getPrecio)
                .sum()
                -
                repository.findById(id)
                        .stream()
                        .filter(j -> j.getFechaLanzamiento().getYear() < 2010)
                        .mapToDouble(Juego::getPrecio)
                        .sum() * (descuentoNormal / 100);
    }

}
