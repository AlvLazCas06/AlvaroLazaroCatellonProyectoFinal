package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                    where concat(J.nombre, J.precio, J.numJugadores, J.cantidad, J.ventas, J.genero, J.id, J.llegadaAlMercado, J.fechaLanzamiento) ilike %?1%
            """)
    Page<Juego> findAll2(String palabraClave, Pageable pageable);

    Page<Juego> findByConsola_Nombre(String consolaNombre, Pageable pageable);

    @Query("""
                SELECT J
                FROM Juego J
                    ORDER BY J.precio ASC
            """)
    List<Juego> ordenarPorPrecioASC();

    @Query("""
                SELECT J
                FROM Juego J
                    ORDER BY J.precio DESC
            """)
    List<Juego> ordenarPorPrecioDESC();

    @Query("""
            select J
            from Juego J
                order by J.nombre ASC
            """)
    List<Juego> ordenarPorNombreASC();

    @Query("""
            select J
            from Juego J
                order by J.nombre desc
            """)
    List<Juego> ordenarPorNombreDESC();

    List<Juego> findJuegoByGeneroIgnoreCase(String genero);

}
