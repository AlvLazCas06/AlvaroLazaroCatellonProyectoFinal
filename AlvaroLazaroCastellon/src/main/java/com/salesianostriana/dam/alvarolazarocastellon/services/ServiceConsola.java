package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceConsola extends BaseServiceImp<Consola, Long, RepositoryConsola> {

    public Consola getById(Long id) {
        return repository.findById(id).get();
    }

    public List<Consola> listAll(String S) {
        if (S != null) {
            return repository.findAll(S)
                    .stream()
                    .filter(c -> c.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }
        return repository.findAll()
                .stream()
                .filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Consola> listAll2(String S) {
        if (S != null) {
            return repository.findAll2(S)
                    .stream()
                    .filter(c -> c.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }
        return repository.findAll()
                .stream()
                .filter(c -> c.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Consola> findNotSell(String S) {
        return repository.findAll(S)
                .stream()
                .filter(c -> c.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Consola> findNewConsoles(String S) {
        return repository.findAll(S)
                .stream()
                .filter(c -> c.getLlegadaAlMercado().isEqual(LocalDate.now()))
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

    public double applyDiscount(Long id) {
        double descuento = 10;
        String fabricante = "Nintendo";
        return repository.findById(id)
                .stream()
                .mapToDouble(Consola::getPrecio)
                .sum()
                - repository.findById(id)
                .stream()
                .filter(c -> c.getFabricante().equalsIgnoreCase(fabricante))
                .mapToDouble(Consola::getPrecio)
                .sum() * (descuento / 100);
    }

    public List<Consola> findByFabricante(String fabricante) {
        return repository.findConsolaByFabricanteIgnoreCase(fabricante)
                .stream()
                .filter(c -> c.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Consola> findByFabricanteOnNotSell(String fabricante) {
        return repository.findConsolaByFabricanteIgnoreCase(fabricante)
                .stream()
                .filter(c -> c.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Consola> findByFabricanteOnNewConsole(String fabricante) {
        return repository.findConsolaByFabricanteIgnoreCase(fabricante)
                .stream()
                .filter(c -> c.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public Optional<Consola> findMaxSell() {
        return repository.findAll()
                .stream()
                .max((c1, c2) -> Integer.compare(c1.getVentas(), c2.getVentas()));
    }

    public List<Consola> findThreeMaxSell() {
        return repository.findAll()
                .stream()
                .sorted((c1, c2) -> Integer.compare(c1.getVentas(), c2.getVentas()))
                .filter(c -> c.getVentas() > 0
                    && !c.equals(findMaxSell().get()))
                .limit(3)
                .toList();
    }

}
