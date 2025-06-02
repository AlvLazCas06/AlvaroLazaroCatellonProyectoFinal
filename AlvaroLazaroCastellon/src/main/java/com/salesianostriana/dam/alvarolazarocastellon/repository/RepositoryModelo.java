package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryModelo extends JpaRepository<Modelo, Long> {

}
