package com.salesianostriana.dam.alvarolazarocastellon.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImp<T, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {

    @Autowired
    protected R repository;

    public T save(T t) {
        return repository.save(t);
    }

    public List<T> findAll() {
        return repository.findAll().stream().toList();
    }

    public T edit(T t) {
        return repository.save(t);
    }

    public void delete(T t) {
        repository.delete(t);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

}
