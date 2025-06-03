package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryConsola extends JpaRepository<Consola, Long> {

    @Query("""
           SELECT C
              FROM Consola C
                         WHERE concat(C.nombre, C.description, C.id) ILIKE %?1%
           """)
    List<Consola> findAll2(String palabraClave);

}
