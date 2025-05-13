package com.salesianostriana.dam.alvarolazarocastellon.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

}
