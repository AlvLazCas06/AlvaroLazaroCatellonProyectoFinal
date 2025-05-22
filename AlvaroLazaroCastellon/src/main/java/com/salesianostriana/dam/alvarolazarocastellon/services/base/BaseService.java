package com.salesianostriana.dam.alvarolazarocastellon.services.base;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {

    T save(T t);

    List<T> findAll();

    T edit(T t);

    void delete(T t);

    Optional<T> findById(ID id);

    void deleteById(ID id);

}
