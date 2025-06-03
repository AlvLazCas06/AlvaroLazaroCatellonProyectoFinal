package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryModelo extends JpaRepository<Modelo, Long> {

    @Query("""
        select M
        from Modelo M
            where concat(M.nombre, M.precio, M.consola.nombre) ilike %?1%
    """)
    List<Modelo> findAll(String palabraClave);

    List<Modelo> findModeloByFabricanteIgnoreCase(String fabricante);

}
