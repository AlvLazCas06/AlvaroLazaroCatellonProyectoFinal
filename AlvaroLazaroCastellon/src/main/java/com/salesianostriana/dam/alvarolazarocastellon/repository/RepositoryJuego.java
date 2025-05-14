package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryJuego extends JpaRepository<Juego, Long> {

    @Query("""
                select J
                from Juego J
                    where concat(J.nombre, J.precio) ilike %?1%
            """)
    List<Juego> findAll(String palabraClave);

    @Query("""
                select J
                from Juego J
                    where concat(J.nombre, J.precio, J.numJugadores, J.cantidad, J.consola.nombre, J.ventas, J.genero, J.id) ilike %?1%
            """)
    List<Juego> findAll2(String palabraClave);

    // List<Juego> findAllById(Long id);

}
