package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryJuego extends JpaRepository<Juego, Long> {
}
