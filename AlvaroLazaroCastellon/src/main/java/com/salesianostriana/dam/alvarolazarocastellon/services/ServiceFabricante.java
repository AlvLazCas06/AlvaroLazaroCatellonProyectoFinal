package com.salesianostriana.dam.alvarolazarocastellon.services;

import com.salesianostriana.dam.alvarolazarocastellon.model.Fabricante;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryFabricante;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceFabricante extends BaseServiceImp<Fabricante, Long, RepositoryFabricante> {

    public Fabricante getById(Long id) {
        return repository.findById(id).get();
    }

    public List<Fabricante> findByName(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return repository.findAll();
        }
        return repository.findFabricanteByNombreIgnoreCase(nombre);
    }

    public boolean deleteFabricante(Long id) {
        if (repository.findById(id).get().getModelos().isEmpty()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
