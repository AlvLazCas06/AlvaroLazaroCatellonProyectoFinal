package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryConsola extends JpaRepository<Consola, Long> {

    @Query("""
        select C
        from Consola C
            where concat(C.nombre, C.precio) ilike %?1%
    """)
    List<Consola> findAll(String palabraClave);

}
