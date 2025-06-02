package com.salesianostriana.dam.alvarolazarocastellon.services;


import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import com.salesianostriana.dam.alvarolazarocastellon.repository.RepositoryModelo;
import com.salesianostriana.dam.alvarolazarocastellon.services.base.BaseServiceImp;
import org.springframework.stereotype.Service;

@Service
public class ServiceModelo extends BaseServiceImp<Modelo, Long, RepositoryModelo> {

    public Modelo getById(Long id) {
        return repository.findById(id).get();
    }

}
