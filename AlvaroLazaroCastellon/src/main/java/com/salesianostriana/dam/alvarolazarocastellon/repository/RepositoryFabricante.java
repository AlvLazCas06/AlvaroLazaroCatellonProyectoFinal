package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryFabricante extends JpaRepository<Fabricante, Long> {

    List<Fabricante> findFabricanteByNombreIgnoreCase(String nombre);

    Optional<Fabricante> findByNombreIgnoreCase(String nombre);
}