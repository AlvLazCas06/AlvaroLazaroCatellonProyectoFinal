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

    @Query("""
            select J, J.consola, count (*) as totalVentas
            from Juego J
                group by J, J.consola
                order by totalVentas DESC
            """)
    List<Juego> buscarEstadisticasDeVenta();

    List<Juego> findJuegoByGeneroIgnoreCase(String genero);

}
