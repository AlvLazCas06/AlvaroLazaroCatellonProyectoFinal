package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryModelo;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Page<Modelo> findAllPage(String s, String nombre, Pageable pageable) {
        if (s != null && nombre.isBlank()) {
            return repository.findAll2(s, pageable);
        } else if ((s == null || s.isBlank()) && nombre != null) {
            return repository.findByConsola_Nombre(nombre, pageable);
        } else if (s != null && nombre != null && !nombre.isBlank()) {
            return repository.findModeloByNombreIgnoreCaseAndConsola_Nombre(s, nombre, pageable);
        } else {
            return repository.findAll(pageable);
        }
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
        return repository.findModeloByFabricante_Nombre(fabricante)
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1)))
                .toList();
    }

    public List<Modelo> findByFabricanteOnNotSell(String fabricante) {
        return repository.findModeloByFabricante_Nombre(fabricante)
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isAfter(LocalDate.now()))
                .toList();
    }

    public List<Modelo> findByFabricanteOnNewConsole(String fabricante) {
        return repository.findModeloByFabricante_Nombre(fabricante)
                .stream()
                .filter(m -> m.getLlegadaAlMercado().isEqual(LocalDate.now()))
                .toList();
    }

    public void applyDiscount(int descuento, String fabricante) {
        repository.findAll().forEach(modelo -> {
            if (modelo.getFabricante().getNombre().equalsIgnoreCase(fabricante)) {
                modelo.setPrecio(modelo.getPrecio() - (modelo.getPrecio() * (descuento / 100.0)));
                edit(modelo);
            }
        });
    }

}
