package com.salesianostriana.dam.alvarolazarocastellon.repository;

import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
        select M
        from Modelo M
            where concat(M.id, M.nombre, M.description, M.fabricante.nombre, M.precio, M.cantidad, M.ventas, M.fechaLanzamiento, M.llegadaAlMercado, M.consola.nombre) ilike %?1%
    """)
    Page<Modelo> findAll2(String palabraClave, Pageable pageable);

    List<Modelo> findModeloByFabricante_Nombre(String fabricante);

    Page<Modelo> findModeloByNombreIgnoreCaseAndConsola_Nombre(String nombre, String consolaNombre, Pageable pageable);

    Page<Modelo> findByConsola_Nombre(String consolaNombre, Pageable pageable);

}
