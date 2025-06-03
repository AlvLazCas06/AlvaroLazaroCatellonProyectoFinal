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

}
