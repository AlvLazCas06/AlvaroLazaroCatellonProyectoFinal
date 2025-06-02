package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
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

    @Query("""
           SELECT C
              FROM Consola C
                         WHERE concat(C.nombre, C.precio, C.cantidad, C.fabricante, C.description, C.ventas, C.llegadaAlMercado, C.fechaLanzamiento, C.id) ILIKE %?1%
           """)
    List<Consola> findAll2(String palabraClave);

    List<Consola> findConsolaByFabricanteIgnoreCase(String fabricante);

}
