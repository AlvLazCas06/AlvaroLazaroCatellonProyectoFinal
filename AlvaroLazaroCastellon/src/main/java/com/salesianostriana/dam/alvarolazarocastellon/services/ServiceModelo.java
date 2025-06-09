package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryModelo;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceModelo extends BaseServiceImp<Modelo, Long, RepositoryModelo> {

    public Modelo getById(Long id) {
        return repository.findById(id).get();
    }

    public List<Modelo> listAll(String S) {
        if (S != null) {
            return repository.findAll(S)
                    .stream()
                    .filter(m -> m.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                    .toList();
        }
        return repository.findAll()
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Modelo> listAll2(String S) {
        if (S != null) {
            return repository.findAll2(S);
        }
        return repository.findAll();
    }

    public List<Modelo> findNotSell(String S) {
        if (S != null) {
            return repository.findAll(S)
                    .stream()
                    .filter(m -> m.getLlegadaAlMercado().isAfter(LocalDate.now()))
                    .toList();
        }
        return findAll()
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Modelo> findNewConsoles(String S) {
        if (S != null) {
            return repository.findAll(S)
                    .stream()
                    .filter(m -> m.getLlegadaAlMercado().isEqual(LocalDate.now()))
                    .toList();
        }
        return findAll()
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public List<Modelo> findNewConsolesForEmail() {
        return findAll()
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public Optional<Modelo> findMaxSell() {
        return repository.findAll()
                .stream()
                .filter(m -> m.getVentas() > 0
                        && m.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .max((m1, m2) -> Integer.compare(m1.getVentas(), m2.getVentas()));
    }

    public List<Modelo> findThreeMaxSell() {
        return repository.findAll()
                .stream()
                .sorted((m1, m2) -> Integer.compare(m2.getVentas(), m1.getVentas()))
                .filter(m -> m.getVentas() > 0
                        && !m.equals(findMaxSell().orElse(null)))
                .limit(3)
                .toList();
    }

    public List<Modelo> findByFabricante(String fabricante) {
        return repository.findModeloByFabricanteIgnoreCase(fabricante)
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Modelo> findByFabricanteOnNotSell(String fabricante) {
        return repository.findModeloByFabricanteIgnoreCase(fabricante)
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Modelo> findByFabricanteOnNewConsole(String fabricante) {
        return repository.findModeloByFabricanteIgnoreCase(fabricante)
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public double applyDiscount(Long id) {
        double descuento = 10;
        String fabricante = "Nintendo";
        return repository.findById(id)
                .stream()
                .mapToDouble(Modelo::getPrecio)
                .sum()
                - repository.findById(id)
                .stream()
                .filter(c -> c.getFabricante().equalsIgnoreCase(fabricante))
                .mapToDouble(Modelo::getPrecio)
                .sum() * (descuento / 100);
    }

}
