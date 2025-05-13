package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryConsola;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceConsola extends BaseServiceImp<Consola, Long, RepositoryConsola> {

    public List<Consola> listAll(String S) {
        if (S != null) {
            return repository.findAll(S).stream().filter(c -> c.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1))).toList();
        }
        return repository.findAll().stream().filter(j -> j.getLlegadaAlMercado().isBefore(LocalDate.now().plusDays(1))).toList();
    }

    public List<Consola> findNotSell() {
        return repository.findAll().stream().filter(c -> c.getLlegadaAlMercado().isAfter(LocalDate.now())).toList();
    }

    public List<Consola> findNewConsoles() {
        return repository.findAll().stream().filter(c -> c.getLlegadaAlMercado().isEqual(LocalDate.now())).toList();
    }

    public boolean deleteConsole(Long id) {
        if (repository.findById(id).get().getJuegos().isEmpty()) {
            repository.deleteById(id);
            return true;

        } else {
            return false;
        }
    }

}
