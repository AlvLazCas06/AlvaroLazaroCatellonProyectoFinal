package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryJuego;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
public class ServiceJuego extends BaseServiceImp<Juego, Long, RepositoryJuego> {

    public Juego getById(Long id) {
        return repository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Page<Juego> findAllPage(String s, String nombre, Pageable pageable) {
        if (s != null && nombre.isBlank()) {
            return repository.findAll2(s, pageable);
        } else if ((s == null || s.isBlank()) && nombre != null) {
            return repository.findByConsola_Nombre(nombre, pageable);
        } else if (s != null && nombre != null && !nombre.isBlank()) {
            return repository.findJuegoByNombreIgnoreCaseAndConsola_Nombre(s, nombre, pageable);
        } else {
            return repository.findAll(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Page<Juego> findByConsoleName(String nombre, Pageable pageable) {
        return repository.findByConsola_Nombre(nombre, pageable);
    }

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

    public Optional<Juego> findMaxSell() {
        return repository.findAll()
                .stream()
                .filter(j -> j.getVentas() > 0
                        && j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .max((j1, j2) -> Integer.compare(j1.getVentas(), j2.getVentas()));
    }

    public List<Juego> findNewsForEmail() {
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
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

    public List<Juego> findByGenreOnNews(String genero) {
        if (genero != null) {
            return repository.findJuegoByGeneroIgnoreCase(genero)
                    .stream()
                    .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                    .toList();
        }
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public List<Juego> findByGenreOnNotSell(String genero) {
        if (genero != null) {
            return repository.findJuegoByGeneroIgnoreCase(genero)
                    .stream()
                    .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                    .toList();
        }
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Juego> findByConsole(String consola, String palabraClave) {
        if (palabraClave != null) {
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

    public List<Juego> findByConsoleOnNews(String consola, String palabraClave) {
        if (palabraClave != null) {
            return repository.findAll(palabraClave).stream()
                    .filter(j -> j.getConsola() != null
                            && j.getConsola().getNombre().equalsIgnoreCase(consola)
                            && j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                    .toList();
        }
        return repository.findAll().stream()
                .filter(j -> j.getConsola() != null
                        && j.getConsola().getNombre().equalsIgnoreCase(consola)
                        && j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public List<Juego> findByConsoleOnNotSell(String consola, String palabraClave) {
        if (palabraClave != null) {
            return repository.findAll(palabraClave).stream()
                    .filter(j -> j.getConsola() != null
                            && j.getConsola().getNombre().equalsIgnoreCase(consola)
                            && j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                    .toList();
        }
        return repository.findAll().stream()
                .filter(j -> j.getConsola() != null
                        && j.getConsola().getNombre().equalsIgnoreCase(consola)
                        && j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Juego> findNotSell(String palabraClave) {

        if (palabraClave != null) {
            return repository.findAll(palabraClave)
                    .stream()
                    .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
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

    public List<Juego> sortGamesOnNews(String sort) {
        List<Juego> juegos;

        switch (sort) {
            case "priceASC" -> {
                juegos = repository.ordenarPorPrecioASC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                        .toList();
            }
            case "priceDESC" -> {
                juegos = repository.ordenarPorPrecioDESC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                        .toList();
            }
            case "A-Z" -> {
                juegos = repository.ordenarPorNombreASC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                        .toList();
            }
            case "Z-A" -> {
                juegos = repository.ordenarPorNombreDESC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                        .toList();
            }
            default -> {
                juegos = repository.findAll()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isEqual(LocalDate.now()))
                        .toList();
            }
        }

        return juegos;
    }

    public List<Juego> sortGamesOnNotSell(String sort) {
        List<Juego> juegos;

        switch (sort) {
            case "priceASC" -> {
                juegos = repository.ordenarPorPrecioASC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                        .toList();
            }
            case "priceDESC" -> {
                juegos = repository.ordenarPorPrecioDESC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                        .toList();
            }
            case "A-Z" -> {
                juegos = repository.ordenarPorNombreASC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                        .toList();
            }
            case "Z-A" -> {
                juegos = repository.ordenarPorNombreDESC()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                        .toList();
            }
            default -> {
                juegos = repository.findAll()
                        .stream()
                        .filter(j -> j.getLlegadaAlMercado().isAfter(LocalDate.now()))
                        .toList();
            }
        }

        return juegos;
    }

    public void applyDiscountByYear(int descuentoNormal, int descuentoLlegada2010, int descuentoLlegadaHoy) {
        repository.findAll()
                .forEach(juego -> {
                    if (juego.getFechaLanzamiento().getYear() < 2010) {
                        juego.setPrecio(juego.getPrecio() - (juego.getPrecio() * (descuentoNormal / 100.0)));
                        edit(juego);
                    } else if (juego.getFechaLanzamiento().isEqual(LocalDate.now())
                            && juego.getFechaLanzamiento().getYear() < 2010) {
                        juego.setPrecio(juego.getPrecio() - (juego.getPrecio() * (descuentoLlegada2010 / 100.0)));
                        edit(juego);
                    } else if (juego.getFechaLanzamiento().isEqual(LocalDate.now())) {
                        juego.setPrecio(juego.getPrecio() - (juego.getPrecio() * (descuentoLlegadaHoy / 100.0)));
                        edit(juego);
                    }
                });
    }

    public Map<Juego, Double> calculatePricePerNumberOfPLayers() {
        Map<Juego, Double> precioPorJugadores = new LinkedHashMap<Juego, Double>();
        repository.findAll()
                .forEach(
                        juego -> {
                            precioPorJugadores.put(juego, juego.getPrecio() / (double) juego.getNumJugadores());
                        }
                );
        return precioPorJugadores;
    }

}
